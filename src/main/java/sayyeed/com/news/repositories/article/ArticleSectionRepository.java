package sayyeed.com.news.repositories.article;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import sayyeed.com.news.entities.article.ArticleSectionEntity;

import java.util.List;

public interface ArticleSectionRepository extends CrudRepository<ArticleSectionEntity, Integer> {

    @Query("select sectionId from ArticleSectionEntity where articleId =?1")
    List<Integer> getSectionIdsByArticleId(Integer articleId);

    @Transactional
    @Modifying
    @Query("delete from ArticleSectionEntity where articleId =?1 and sectionId =?2")

    void deleteByArticleIdAndSectionId(Integer articleId, Integer sectionId);

}

