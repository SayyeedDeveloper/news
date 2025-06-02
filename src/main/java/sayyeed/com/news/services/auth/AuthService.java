package sayyeed.com.news.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sayyeed.com.news.dtos.auth.RegistrationDTO;
import sayyeed.com.news.entities.profile.ProfileEntity;
import sayyeed.com.news.enums.profile.ProfileRoleEnum;
import sayyeed.com.news.enums.profile.ProfileStatusEnum;
import sayyeed.com.news.exceptions.AppBadException;
import sayyeed.com.news.repositories.profile.ProfileRepository;
import sayyeed.com.news.services.email.EmailHistoryService;
import sayyeed.com.news.services.email.EmailSenderService;
import sayyeed.com.news.services.profile.ProfileRoleService;

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

    public String registration(RegistrationDTO dto) {
        // 1. validation TODO in DTO class
        // 2.   1213
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

        // send verificationCode
        emailSenderService.sendVerificationLink(profile.getUsername());

        return "Verification Code sent!";
    }

    public String verificationByLink(String username, String code) {
        Boolean flag = emailHistoryService.isEmailSend(username, code);
        if (flag == true) {
            return "Verification Success!";
        }
        return "Something went wrong!";
    }

}
