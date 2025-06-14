package sayyeed.com.news.services.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sayyeed.com.news.dtos.JwtDTO;
import sayyeed.com.news.dtos.article.ArticleCreateDTO;
import sayyeed.com.news.dtos.article.ArticleInfoDTO;
import sayyeed.com.news.dtos.article.ArticleUpdateDTO;
import sayyeed.com.news.entities.CategoryEntity;
import sayyeed.com.news.entities.RegionEntity;
import sayyeed.com.news.entities.SectionEntity;
import sayyeed.com.news.entities.article.ArticleEntity;
import sayyeed.com.news.entities.profile.ProfileEntity;
import sayyeed.com.news.enums.article.ArticleStatusEnum;
import sayyeed.com.news.exceptions.AppBadException;
import sayyeed.com.news.repositories.CategoryRepository;
import sayyeed.com.news.repositories.RegionRepository;
import sayyeed.com.news.repositories.SectionRepository;
import sayyeed.com.news.repositories.article.ArticleRepository;
import sayyeed.com.news.services.profile.ProfileService;
import sayyeed.com.news.utils.JwtUtil;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository repository;

    @Autowired
    private ArticleCategoryService articleCategoryService;

    @Autowired
    private ArticleSectionService articleSectionService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SectionRepository sectionRepository;


    public ArticleInfoDTO create(ArticleCreateDTO createDTO, String token){
        // cleaning token
        String cleanToken = token.substring(7).trim();
        JwtDTO jwtDTO = JwtUtil.decode(cleanToken);

        //checking the region
        Optional<RegionEntity> optional = regionRepository.findByIdAndVisibleIsTrue(createDTO.getRegionId());
        if (optional.isEmpty()) {
            throw new AppBadException("Region not found");
        }

        for (Integer categoryId : createDTO.getCategories()) {
            Optional<CategoryEntity> categoryOptional = categoryRepository.findByIdAndVisibleIsTrue(categoryId);
            if (categoryOptional.isEmpty()) {
                throw new AppBadException("Category " + categoryId + " not found");
            }
        }

        for (Integer sectionId : createDTO.getSections()) {
            Optional<SectionEntity> sectionOptional = sectionRepository.findByIdAndVisibleIsTrue(sectionId);
            if (sectionOptional.isEmpty()) {
                throw new AppBadException("Section " + sectionId + " not found");
            }
        }

        // getting moderator user
        ProfileEntity profileEntity = profileService.getByUsername(jwtDTO.getUsername());

        ArticleEntity entity = new ArticleEntity();

        //set moderator id
        entity.setModeratorId(entity.getId());

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

    public ArticleInfoDTO update(Integer id, ArticleUpdateDTO updateDTO){
        Optional<ArticleEntity> optional = repository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadException("Article not found");
        }
        ArticleEntity entity = optional.get();
        entity.setTitle(updateDTO.getTitle());
        entity.setDescription(updateDTO.getDescription());
        entity.setContent(updateDTO.getContent());
        entity.setImageId(updateDTO.getImageId());
        entity.setRegionId(updateDTO.getRegionId());

        repository.save(entity);

        // set categories
        articleCategoryService.merge(id, updateDTO.getCategories());

        // set sections
        articleSectionService.merge(id, updateDTO.getSections());

        return toArticleInfoDTO(entity);
    }

    public String deleteArticle(Integer id){

        Optional<ArticleEntity> optional = repository.findById(id);
        if (optional.isEmpty()){
            throw new AppBadException("Article not found");
        }

        // delete Article from ArticleCategory table
        articleCategoryService.deleteArticleCategoryByArticleId(id);

        // delete Article from ArticleSection table
        articleSectionService.deleteArticleSectionByArticleId(id);

        repository.delete(optional.get());
        return "Article deleted successfully";
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
