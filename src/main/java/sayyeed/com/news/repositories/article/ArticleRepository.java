package sayyeed.com.news.repositories.article;

import org.springframework.data.repository.CrudRepository;
import sayyeed.com.news.entities.article.ArticleEntity;

public interface ArticleRepository extends CrudRepository<ArticleEntity, Integer> {

}
