package sayyeed.com.news.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sayyeed.com.news.dtos.profile.ProfileDTO;
import sayyeed.com.news.dtos.profile.ProfileInfoDTO;
import sayyeed.com.news.entities.profile.ProfileEntity;
import sayyeed.com.news.entities.profile.ProfileRoleEntity;
import sayyeed.com.news.enums.ProfileStatusEnum;
import sayyeed.com.news.enums.UserRoleEnum;
import sayyeed.com.news.exceptions.AppBadException;
import sayyeed.com.news.repositories.profile.ProfileRepository;
import sayyeed.com.news.repositories.profile.ProfileRoleRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository repository;

    @Autowired
    private ProfileRoleRepository roleRepository;

    public ProfileInfoDTO create(ProfileDTO dto){
        Optional<ProfileEntity> optional = repository.findByUsernameAndVisibleTrue(dto.getUsername());
        if (optional.isPresent()){
            throw new AppBadException("Username " + dto.getUsername() + " exist");
        }

        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setName(dto.getName());
        profileEntity.setSurname(dto.getSurname());
        profileEntity.setUsername(dto.getUsername());
        profileEntity.setPassword(dto.getPassword());
        profileEntity.setPhotoId(dto.getPhotoId());
        profileEntity.setStatus(ProfileStatusEnum.ACTIVE);
        profileEntity.setCreatedDate(LocalDateTime.now());
        profileEntity.setVisible(Boolean.TRUE);
        repository.save(profileEntity);

        List<UserRoleEnum> roles = dto.getRoles();
        for (UserRoleEnum role : roles) {
            Optional<ProfileRoleEntity> roleOptional = roleRepository.findByRole(role);
            if (roleOptional.isPresent()){
                continue;
            }
            ProfileRoleEntity roleEntity = new ProfileRoleEntity();
            roleEntity.setProfileEntity(profileEntity);
            roleEntity.setRole(role);
            roleRepository.save(roleEntity);
        }

        return toProfileInfoDto(profileEntity, dto.getRoles());
    }

    public ProfileInfoDTO toProfileInfoDto(ProfileEntity entity, List<UserRoleEnum> roles){
        ProfileInfoDTO profileInfoDTO = new ProfileInfoDTO();
        profileInfoDTO.setId(entity.getId());
        profileInfoDTO.setName(entity.getName());
        profileInfoDTO.setSurname(entity.getSurname());
        profileInfoDTO.setUsername(entity.getUsername());
        profileInfoDTO.setStatus(entity.getStatus());
        profileInfoDTO.setVisible(entity.getVisible());
        profileInfoDTO.setCreatedDate(entity.getCreatedDate());
        profileInfoDTO.setPhotoId(entity.getPhotoId());
        profileInfoDTO.setRoles(roles);
        return profileInfoDTO;
    }
}
