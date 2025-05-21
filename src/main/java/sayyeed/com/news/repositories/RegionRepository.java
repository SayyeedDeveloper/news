package sayyeed.com.news.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import sayyeed.com.news.entities.RegionEntity;

import java.util.List;
import java.util.Optional;

public interface RegionRepository extends CrudRepository<RegionEntity, Integer> {

    Optional<RegionEntity> findByIdAndVisibleIsTrue(Integer id);

    @Transactional
    @Modifying
    @Query("update RegionEntity set visible = false where id = ?1")
    int updateVisibleById(Integer id);

    @Query("from RegionEntity where visible = true order by orderNumber")
    List<RegionEntity> findAllByOrderNumberSorted();

    Optional<RegionEntity> findByOrderNumber(Integer number);
}
