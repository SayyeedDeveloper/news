package sayyeed.com.news.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import sayyeed.com.news.entities.AttachEntity;

public interface AttachRepository extends CrudRepository<AttachEntity, String> {

    @Query("from AttachEntity where visible = true order by createdDate desc")
    Page<AttachEntity> findAllByOrderByCreatedDateDesc(Pageable pageable);

}
