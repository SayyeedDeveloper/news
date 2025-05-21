package sayyeed.com.news.Repositories;

import org.springframework.data.repository.CrudRepository;
import sayyeed.com.news.Entities.CategoryEntity;

public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer> {
}
