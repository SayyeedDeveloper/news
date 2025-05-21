package sayyeed.com.news.Repositories;

import org.springframework.data.repository.CrudRepository;
import sayyeed.com.news.Entities.SectionEntity;

public interface SectionRepository extends CrudRepository<SectionEntity, Integer> {
}
