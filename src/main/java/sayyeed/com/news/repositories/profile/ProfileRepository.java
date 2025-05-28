package sayyeed.com.news.repositories.profile;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import sayyeed.com.news.entities.profile.ProfileEntity;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer> {

    Optional<ProfileEntity> findByUsernameAndVisibleTrue(String username);

    Optional<ProfileEntity> findByIdAndVisibleIsTrue(Integer id);

    @Query("from ProfileEntity where visible = true order by createdDate desc")
    Page<ProfileEntity> findAllByOrderByCreatedDateDesc(Pageable pageable);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set visible = false where id = ?1")
    int updateVisibleFalse(Integer id);

}
