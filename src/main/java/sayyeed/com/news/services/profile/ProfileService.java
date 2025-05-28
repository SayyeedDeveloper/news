package sayyeed.com.news.services.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sayyeed.com.news.dtos.FilterResultDTO;
import sayyeed.com.news.dtos.profile.ProfileCreateDTO;
import sayyeed.com.news.dtos.profile.ProfileFilterDTO;
import sayyeed.com.news.dtos.profile.ProfileInfoDTO;
import sayyeed.com.news.dtos.profile.ProfileUpdateDTO;
import sayyeed.com.news.entities.profile.ProfileEntity;
import sayyeed.com.news.enums.ProfileStatusEnum;
import sayyeed.com.news.enums.ProfileRoleEnum;
import sayyeed.com.news.exceptions.AppBadException;
import sayyeed.com.news.repositories.profile.ProfileFilterRepository;
import sayyeed.com.news.repositories.profile.ProfileRepository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository repository;

    @Autowired
    private ProfileRoleService profileRoleService;

    @Autowired
    private ProfileFilterRepository filterRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public ProfileInfoDTO create(ProfileCreateDTO dto){
        Optional<ProfileEntity> optional = repository.findByUsernameAndVisibleTrue(dto.getUsername());
        if (optional.isPresent()){
            throw new AppBadException("Username " + dto.getUsername() + " exist");
        }

        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setName(dto.getName());
        profileEntity.setSurname(dto.getSurname());
        profileEntity.setUsername(dto.getUsername());
        profileEntity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        profileEntity.setPhotoId(dto.getPhotoId());
        profileEntity.setStatus(ProfileStatusEnum.ACTIVE);
        profileEntity.setCreatedDate(LocalDateTime.now());
        profileEntity.setVisible(Boolean.TRUE);
        repository.save(profileEntity);

        profileRoleService.merge(profileEntity.getId(), dto.getRoles());

        return toProfileInfoDto(profileEntity);
    }

    public ProfileInfoDTO update(Integer id, ProfileUpdateDTO dto){
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
        entity.setStatus(entity.getStatus());
        entity.setPhotoId(dto.getPhotoId());
        repository.save(entity);

        profileRoleService.merge(entity.getId(), dto.getRoles());

        return toProfileInfoDto(entity);
    }


    public Page<ProfileInfoDTO> getAll(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<ProfileEntity> entities = repository.findAllByOrderByCreatedDateDesc(pageable);

        List<ProfileEntity> entityList = entities.getContent();
        long totalElement = entities.getTotalElements();

        List<ProfileInfoDTO> dtos = new LinkedList<>();
        entityList.forEach(entity -> dtos.add(toProfileInfoDto(entity)));

        return new PageImpl<ProfileInfoDTO>(dtos, pageable, totalElement);
    }


    public Page<ProfileInfoDTO> filter(ProfileFilterDTO filterDTO, int page, int size){
        FilterResultDTO<ProfileEntity> result = filterRepository.filter(filterDTO, page, size);
        List<ProfileInfoDTO> dtos = new LinkedList<>();
        result.getContent().forEach(entity -> dtos.add(toProfileInfoDto(entity)));
        return new  PageImpl<>(dtos, PageRequest.of(page, size), result.getTotalCount());
    }


    public ProfileInfoDTO toProfileInfoDto(ProfileEntity entity){
        ProfileInfoDTO profileInfoDTO = new ProfileInfoDTO();
        profileInfoDTO.setId(entity.getId());
        profileInfoDTO.setName(entity.getName());
        profileInfoDTO.setSurname(entity.getSurname());
        profileInfoDTO.setUsername(entity.getUsername());
        profileInfoDTO.setStatus(entity.getStatus());
        profileInfoDTO.setCreatedDate(entity.getCreatedDate());
        profileInfoDTO.setPhotoId(entity.getPhotoId());
        profileInfoDTO.setRoles(profileRoleService.getRolesById(profileInfoDTO.getId()));
        return profileInfoDTO;
    }
}
