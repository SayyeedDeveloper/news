package sayyeed.com.news.repositories.profile;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import sayyeed.com.news.entities.profile.ProfileRoleEntity;
import sayyeed.com.news.enums.UserRoleEnum;

import java.util.List;

public interface ProfileRoleRepository extends CrudRepository<ProfileRoleEntity, Integer> {

    @Query("select role from ProfileRoleEntity where profileId =?1")
    List<UserRoleEnum> getRoleListByProfileId(Integer profileId);

    @Transactional
    @Modifying
    @Query("delete from  ProfileRoleEntity where profileId =?1 and role =?2")
    void deleteByIdAndRoleEnum(Integer profileId, UserRoleEnum roleEnum);
}
