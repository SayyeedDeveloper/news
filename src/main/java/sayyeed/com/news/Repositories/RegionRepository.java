package sayyeed.com.news.Repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import sayyeed.com.news.Entities.RegionEntity;

import java.util.List;
import java.util.Optional;

public interface RegionRepository extends CrudRepository<RegionEntity, Integer> {
    List<RegionEntity> findAllByVisibleIsTrue();

    @Query("from RegionEntity order by order_number")
    List<RegionEntity> findAllByOrder_numberSorted();
}
