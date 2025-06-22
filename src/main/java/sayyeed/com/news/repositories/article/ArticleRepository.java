package sayyeed.com.news.repositories.article;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import sayyeed.com.news.entities.article.ArticleEntity;
import sayyeed.com.news.enums.article.ArticleStatusEnum;

public interface ArticleRepository extends CrudRepository<ArticleEntity, Integer> {
    @Modifying
    @Transactional
    @Query("update ArticleEntity set status =?1 where id =?2")
    void changeStatusById(ArticleStatusEnum status, Integer id);

    @Query("SELECT a FROM ArticleEntity a JOIN a.articleSections s WHERE a.visible = true AND s.sectionId = :sectionId ORDER BY a.createdDate DESC")
    Page<ArticleEntity> getArticleBySectionId(Integer sectionId, Pageable pageable);
}
