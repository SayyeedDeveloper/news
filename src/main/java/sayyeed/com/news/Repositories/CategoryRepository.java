package sayyeed.com.news.Repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import sayyeed.com.news.Entities.CategoryEntity;

import java.util.List;

public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer> {

    @Query("from CategoryEntity where visible = true order by order_number")
    List<CategoryEntity> getAllByOrderSorted();

}
