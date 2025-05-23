package sayyeed.com.news.repositories.profile;

import org.springframework.data.repository.CrudRepository;
import sayyeed.com.news.entities.profile.ProfileRoleEntity;
import sayyeed.com.news.enums.UserRoleEnum;

import java.util.Optional;

public interface ProfileRoleRepository extends CrudRepository<ProfileRoleEntity, Integer> {
    Optional<ProfileRoleEntity> findByRole(UserRoleEnum role);
}
