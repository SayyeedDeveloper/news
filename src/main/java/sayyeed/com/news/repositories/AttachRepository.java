package sayyeed.com.news.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import sayyeed.com.news.entities.AttachEntity;

import java.util.Optional;

public interface AttachRepository extends CrudRepository<AttachEntity, String> {

    @Query("from AttachEntity where visible = true order by createdDate desc")
    Page<AttachEntity> findAllByOrderByCreatedDateDesc(Pageable pageable);

    @Query("from AttachEntity where visible = true and id =?1")
    Optional<AttachEntity> findByIdAndVisibleTrue(String id);

    @Modifying
    @Transactional
    @Query("update AttachEntity set visible = false where id =?1")
    void setVisibleFalse(String id);
}
