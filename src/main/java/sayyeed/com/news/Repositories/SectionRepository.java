package sayyeed.com.news.Repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import sayyeed.com.news.Entities.SectionEntity;

import java.util.List;
import java.util.Optional;

public interface SectionRepository extends CrudRepository<SectionEntity, Integer> {
    @Query("from SectionEntity where visible = true order by orderNumber")
    List<SectionEntity> getAllByOrderSorted();


    @Transactional
    @Modifying
    @Query("update SectionEntity set visible = false where id = ?1")
    int updateVisibleById(Integer id);

    Optional<SectionEntity> findByIdAndVisibleIsTrue(Integer id);

    Optional<SectionEntity> findByOrderNumber(Integer orderNumber);
}
