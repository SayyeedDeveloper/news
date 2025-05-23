package sayyeed.com.news.repositories.profile;

import org.springframework.data.repository.CrudRepository;
import sayyeed.com.news.entities.profile.ProfileEntity;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer> {
    Optional<ProfileEntity> findByUsernameAndVisibleTrue(String username);
}
