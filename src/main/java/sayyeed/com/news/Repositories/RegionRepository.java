package sayyeed.com.news.Repositories;

import org.springframework.data.repository.CrudRepository;
import sayyeed.com.news.Entities.RegionEntity;

import java.util.Optional;

public interface RegionRepository extends CrudRepository<RegionEntity, Integer> {
}
