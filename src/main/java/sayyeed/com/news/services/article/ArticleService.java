package sayyeed.com.news.services.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sayyeed.com.news.dtos.article.ArticleCreateDTO;
import sayyeed.com.news.dtos.article.ArticleInfoDTO;
import sayyeed.com.news.entities.article.ArticleEntity;
import sayyeed.com.news.enums.article.ArticleStatusEnum;
import sayyeed.com.news.repositories.article.ArticleRepository;

import java.time.LocalDateTime;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository repository;

    @Autowired
    private ArticleCategoryService articleCategoryService;

    @Autowired
    private ArticleSectionService articleSectionService;


    public ArticleInfoDTO create(ArticleCreateDTO createDTO){
        ArticleEntity entity = new ArticleEntity();
        // Basic article properties
        entity.setTitle(createDTO.getTitle());
        entity.setDescription(createDTO.getDescription());
        entity.setContent(createDTO.getContent());
        entity.setImageId(createDTO.getImageId());
        entity.setRegionId(createDTO.getRegionId());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setStatus(ArticleStatusEnum.NOT_PUBLISHED);
        entity.setVisible(true);
        entity.setViewCount(0);
        entity.setSharedCount(0);

        repository.save(entity);

        // saving categories
        articleCategoryService.merge(entity.getId(), createDTO.getCategories());

        // saving sections
        articleSectionService.merge(entity.getId(), createDTO.getSections());

        return toArticleInfoDTO(entity);
    }

    public ArticleInfoDTO toArticleInfoDTO(ArticleEntity entity){
        ArticleInfoDTO dto = new ArticleInfoDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
        dto.setImageId(entity.getImageId());
        dto.setRegionId(entity.getRegionId());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setReadTime(entity.getReadTime());
        dto.setCategories(articleCategoryService.getCategoryIds(entity.getId()));
        dto.setSections(articleSectionService.getSectionIdsByArticleId(entity.getId()));
        return dto;
    }
}
