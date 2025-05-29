package sayyeed.com.news.repositories.article;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import sayyeed.com.news.entities.article.ArticleCategoryEntity;

import java.util.List;

public interface ArticleCategoryRepository extends CrudRepository<ArticleCategoryEntity, Integer> {

    @Query("select categoryId from ArticleCategoryEntity where articleId =?1")
    List<Integer> getCategoryIdsByArticleId(Integer id);

    @Transactional
    @Modifying
    @Query("delete from ArticleCategoryEntity where articleId =?1 and categoryId =?2")
    void deleteByArticleIdAndCategoryId(Integer articleId, Integer categoryId);


}
