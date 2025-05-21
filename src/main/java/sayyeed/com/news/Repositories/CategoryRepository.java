package sayyeed.com.news.Repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import sayyeed.com.news.Entities.CategoryEntity;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer> {

    @Query("from CategoryEntity where visible = true order by order_number")
    List<CategoryEntity> getAllByOrderSorted();


    @Transactional
    @Modifying
    @Query("update CategoryEntity set visible = false where id = ?1")
    int updateVisibleById(Integer id);

    Optional<CategoryEntity> findByIdAndVisibleIsTrue(Integer id);
}
