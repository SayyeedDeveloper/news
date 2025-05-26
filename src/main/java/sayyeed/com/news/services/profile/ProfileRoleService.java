package sayyeed.com.news.services.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sayyeed.com.news.entities.profile.ProfileRoleEntity;
import sayyeed.com.news.enums.UserRoleEnum;
import sayyeed.com.news.repositories.profile.ProfileRoleRepository;

import java.util.List;

@Service
public class ProfileRoleService {

    @Autowired
    private ProfileRoleRepository roleRepository;

    public void merge(Integer profileId, List<UserRoleEnum> newList){
        List<UserRoleEnum> oldList =  roleRepository.getRoleListByProfileId(profileId);
        newList.stream().filter(n -> !oldList.contains(n)).forEach(pe -> create(profileId, pe));
        oldList.stream().filter(old -> !newList.contains(old)).forEach(pe -> roleRepository.deleteByIdAndRoleEnum(profileId, pe));
    }

    public void create(Integer profileId, UserRoleEnum role){
        ProfileRoleEntity entity = new ProfileRoleEntity();
        entity.setProfileId(profileId);
        entity.setRole(role);
        roleRepository.save(entity);
    }
}
