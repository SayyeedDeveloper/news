package sayyeed.com.news.repositories.profile;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.CrudRepository;
import sayyeed.com.news.entities.profile.ProfileEntity;
import sayyeed.com.news.enums.profile.ProfileStatusEnum;

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

    @Transactional
    @Modifying
    @Query("update ProfileEntity set status =?1 where username =?2")
    void setStatusByUsername(ProfileStatusEnum status, String username);
}
