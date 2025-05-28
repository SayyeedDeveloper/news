package sayyeed.com.news.services.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sayyeed.com.news.entities.profile.ProfileRoleEntity;
import sayyeed.com.news.enums.ProfileRoleEnum;
import sayyeed.com.news.repositories.profile.ProfileRoleRepository;

import java.util.List;

@Service
public class ProfileRoleService {

    @Autowired
    private ProfileRoleRepository roleRepository;

    public void merge(Integer profileId, List<ProfileRoleEnum> newList){
        List<ProfileRoleEnum> oldList =  roleRepository.getRoleListByProfileId(profileId);
        newList.stream().filter(n -> !oldList.contains(n)).forEach(pe -> create(profileId, pe));
        oldList.stream().filter(old -> !newList.contains(old)).forEach(pe -> roleRepository.deleteByIdAndRoleEnum(profileId, pe));
    }

    public void create(Integer profileId, ProfileRoleEnum role){
        ProfileRoleEntity entity = new ProfileRoleEntity();
        entity.setProfileId(profileId);
        entity.setRole(role);
        roleRepository.save(entity);
    }

    public List<ProfileRoleEnum> getRolesById(Integer profileId){
        return roleRepository.getRoleListByProfileId(profileId);
    }
}
