package sayyeed.com.news.services.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sayyeed.com.news.dtos.profile.CreateProfileDTO;
import sayyeed.com.news.dtos.profile.ProfileInfoDTO;
import sayyeed.com.news.dtos.profile.UpdateProfileDto;
import sayyeed.com.news.entities.profile.ProfileEntity;
import sayyeed.com.news.enums.ProfileStatusEnum;
import sayyeed.com.news.enums.UserRoleEnum;
import sayyeed.com.news.exceptions.AppBadException;
import sayyeed.com.news.repositories.profile.ProfileRepository;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository repository;

    @Autowired
    private ProfileRoleService profileRoleService;

    public ProfileInfoDTO create(CreateProfileDTO dto){
        Optional<ProfileEntity> optional = repository.findByUsernameAndVisibleTrue(dto.getUsername());
        if (optional.isPresent()){
            throw new AppBadException("Username " + dto.getUsername() + " exist");
        }

        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setName(dto.getName());
        profileEntity.setSurname(dto.getSurname());
        profileEntity.setUsername(dto.getUsername());
        profileEntity.setPassword(getMd5Hash(dto.getPassword()));  //TODO Need to change MD5/ByCript
        profileEntity.setPhotoId(dto.getPhotoId());
        profileEntity.setStatus(ProfileStatusEnum.ACTIVE);
        profileEntity.setCreatedDate(LocalDateTime.now());
        profileEntity.setVisible(Boolean.TRUE);
        repository.save(profileEntity);

        profileRoleService.merge(profileEntity.getId(), dto.getRoles());

        return toProfileInfoDto(profileEntity, dto.getRoles());
    }

    public ProfileInfoDTO update(Integer id, UpdateProfileDto dto){
        Optional<ProfileEntity> optional = repository.findByIdAndVisibleIsTrue(id);
        if (optional.isEmpty()){
            throw new AppBadException("Profile not found"); //TODO Profile update isn't finished.
        }

        Optional<ProfileEntity> userNameOptional = repository.findByUsernameAndVisibleTrue(dto.getUsername());
        if (userNameOptional.isPresent() && !userNameOptional.get().getId().equals(id)){
            throw new AppBadException("Username present");
        }

        ProfileEntity entity = optional.get();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setUsername(dto.getUsername());
        entity.setStatus(dto.getStatus());
        entity.setPhotoId(dto.getPhotoId());
        repository.save(entity);

        profileRoleService.merge(entity.getId(), dto.getRoles());

        return toProfileInfoDto(entity, dto.getRoles());
    }

    public ProfileInfoDTO toProfileInfoDto(ProfileEntity entity, List<UserRoleEnum> roles){
        ProfileInfoDTO profileInfoDTO = new ProfileInfoDTO();
        profileInfoDTO.setId(entity.getId());
        profileInfoDTO.setName(entity.getName());
        profileInfoDTO.setSurname(entity.getSurname());
        profileInfoDTO.setUsername(entity.getUsername());
        profileInfoDTO.setStatus(entity.getStatus());
        profileInfoDTO.setCreatedDate(entity.getCreatedDate());
        profileInfoDTO.setPhotoId(entity.getPhotoId());
        profileInfoDTO.setRoles(roles);
        return profileInfoDTO;
    }

    private String getMd5Hash(String input){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32){
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 hashing failed", e);
        }
    }
}
