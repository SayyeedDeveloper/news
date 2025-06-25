package sayyeed.com.news.repositories.article;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import sayyeed.com.news.mapper.ArticleShortInfo;
import sayyeed.com.news.entities.article.ArticleEntity;
import sayyeed.com.news.enums.article.ArticleStatusEnum;

import java.time.LocalDateTime;
import java.util.List;

public interface ArticleRepository extends CrudRepository<ArticleEntity, Integer> {

    @Modifying
    @Transactional
    @Query("update ArticleEntity set status =?1, publishedDate =?3 where id =?2")
    void changeStatusById(ArticleStatusEnum status, Integer id, LocalDateTime now);

//    @Query("SELECT a FROM ArticleEntity a JOIN a.articleSections s WHERE a.visible = true AND s.sectionId = :sectionId ORDER BY a.createdDate DESC")
//    Page<ArticleEntity> getArticleBySectionId(Integer sectionId, Pageable pageable);

    // Replace direct limit in JPQL with Pageable
    @Query("select a.id as id, a.title as title, a.description as description, a.imageId as imageId, a.publishedDate as publishedDate " +
            "from ArticleEntity a " +
            "inner join ArticleSectionEntity ac " +
            "where ac.sectionId = ?1 and a.visible = true and a.status = 'PUBLISHED' " +
            "order by a.createdDate desc")
    List<ArticleShortInfo> getArticleBySectionId(Integer sectionId, Pageable pageable);

    // Fix the other similar queries the same way
    @Query("select a.id as id, a.title as title, a.description as description, a.imageId as imageId, a.publishedDate as publishedDate " +
            "from ArticleEntity a " +
            "where a.id not in :excludeIds and a.status = 'PUBLISHED' " +
            "order by a.createdDate desc")
    List<ArticleShortInfo> findLatestPublishedArticlesExcept(List<Integer> excludeIds, Pageable pageable);

    @Query("select a.id as id, a.title as title, a.description as description, a.imageId as imageId, a.publishedDate as publishedDate " +
            "from ArticleEntity a " +
            "inner join ArticleCategoryEntity ac " +
            "where ac.categoryId = ?1 and a.visible = true and a.status = 'PUBLISHED' " +
            "order by a.createdDate desc")
    List<ArticleShortInfo> getArticleByCategoryId(Integer categoryId, Pageable pageable);

    @Query("from ArticleEntity where visible = true and regionId =?1 order by createdDate desc ")
    Page<ArticleEntity> getArticleByRegionId(Integer regionId, Pageable pageable);


}
