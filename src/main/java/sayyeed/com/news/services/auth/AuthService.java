package sayyeed.com.news.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sayyeed.com.news.dtos.JwtDTO;
import sayyeed.com.news.dtos.auth.LoginDTO;
import sayyeed.com.news.dtos.auth.RegistrationDTO;
import sayyeed.com.news.dtos.auth.VerificationBySmsDTO;
import sayyeed.com.news.dtos.profile.ProfileInfoDTO;
import sayyeed.com.news.entities.EmailHistoryEntity;
import sayyeed.com.news.entities.profile.ProfileEntity;
import sayyeed.com.news.entities.sms.SmsHistoryEntity;
import sayyeed.com.news.enums.profile.ProfileRoleEnum;
import sayyeed.com.news.enums.profile.ProfileStatusEnum;
import sayyeed.com.news.exceptions.AppBadException;
import sayyeed.com.news.repositories.profile.ProfileRepository;
import sayyeed.com.news.services.email.EmailHistoryService;
import sayyeed.com.news.services.email.EmailSenderService;
import sayyeed.com.news.services.profile.ProfileRoleService;
import sayyeed.com.news.services.profile.ProfileService;
import sayyeed.com.news.services.sms.SmsHistoryService;
import sayyeed.com.news.services.sms.SmsSenderService;
import sayyeed.com.news.utils.JwtUtil;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ProfileRoleService profileRoleService;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private EmailHistoryService emailHistoryService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private SmsSenderService smsSenderService;

    @Autowired
    private SmsHistoryService smsHistoryService;

    public String registration(RegistrationDTO dto) {
        // 1. validation TODO in DTO class
        Optional<ProfileEntity> existOptional = profileRepository.findByUsernameAndVisibleTrue(dto.getUsername());
        if (existOptional.isPresent()) {
            ProfileEntity existsProfile = existOptional.get();
            if (existsProfile.getStatus().equals(ProfileStatusEnum.NOT_ACTIVE)) {
                profileRoleService.deleteRolesByProfileId(existsProfile.getId());
                profileRepository.deleteById(existsProfile.getId()); // delete
            } else {
                throw new AppBadException("Username already exists");
            }
        }
        // create profile
        ProfileEntity profile = new ProfileEntity();
        profile.setName(dto.getName());
        profile.setSurname(dto.getSurname());
        profile.setUsername(dto.getUsername());
        profile.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        profile.setVisible(true);
        profile.setStatus(ProfileStatusEnum.NOT_ACTIVE);
        profileRepository.save(profile);

        // create profile roles
        profileRoleService.create(profile.getId(), ProfileRoleEnum.ROLE_USER);


        if (dto.getUsername().contains("@")) {
            emailSenderService.sendVerificationHtml(profile.getUsername());
        } else {
            smsSenderService.sendRegistrationSMS(profile.getUsername());
        }


        return "Verification Code sent!";
    }

    public String verificationByLink(String token) {
        JwtDTO jwtDTO = JwtUtil.decodeForRegister(token);
        String username = jwtDTO.getUsername();
        String code = jwtDTO.getCode();

        EmailHistoryEntity entity = emailHistoryService.isEmailSent(username);

        // expired time
        LocalDateTime expiredTime = entity.getSentTime().plusMinutes(30);
        if (LocalDateTime.now().isAfter(expiredTime)) {
            return "Something went wrong!";
        }

        // check attempt count
        Integer attempts = entity.getAttemptCount();

        // increase attempt count
        emailHistoryService.incrementAttemptCountById(entity.getId());

        if (attempts >= 5) {
            return "Something went wrong!";
        }
        if (code.equals(entity.getCode())) {
            profileService.setStatusByUsername(ProfileStatusEnum.ACTIVE, username);
            return "Verification Success!";
        }
        return "Something went wrong!";
    }

    public String verificationBySms(VerificationBySmsDTO dto) {

        SmsHistoryEntity smsHistoryEntity = smsHistoryService.getSmsByPhone(dto.getPhoneNumber());

        // expired time
        LocalDateTime expiredTime = smsHistoryEntity.getDateTime().plusMinutes(1);
        // check time
        if (LocalDateTime.now().isAfter(expiredTime)) {
            return "Time Out!";
        }

        // get attempts
        Integer attempts = smsHistoryEntity.getAttemptCount();

        //increase attempt count
        smsHistoryService.increaseAttempt(smsHistoryEntity.getId());

        if (attempts >= 5) {
            return "Too many attempts";
        }

        if (dto.getCode().equals(smsHistoryEntity.getCode())) {
            profileService.setStatusByUsername(ProfileStatusEnum.ACTIVE, dto.getPhoneNumber());
            return "Verification Success!";
        }

        return "Something went wrong!";
    }

    public ProfileInfoDTO login(LoginDTO dto) {

        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleTrue(dto.getUsername());
        if (optional.isEmpty()){
            throw new AppBadException("username or password wrong");
        }
        ProfileEntity entity = optional.get();

        if (entity.getStatus() != ProfileStatusEnum.ACTIVE) {
            throw new AppBadException("username or password wrong");
        }

        boolean flag = bCryptPasswordEncoder.matches(dto.getPassword(), entity.getPassword());

        if (flag) {
            ProfileInfoDTO responseDto  = profileService.toProfileInfoDto(entity);
            responseDto.setJwt(JwtUtil.encode(responseDto.getUsername(), responseDto.getRoles()));
            return responseDto;
        }
        throw new AppBadException("username or password wrong");
    }

}
