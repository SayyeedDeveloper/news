package sayyeed.com.news.services.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sayyeed.com.news.dtos.CategoryDTO;
import sayyeed.com.news.dtos.SectionDTO;
import sayyeed.com.news.dtos.article.ArticleChangeStatusDTO;
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
import sayyeed.com.news.services.AttachService;
import sayyeed.com.news.utils.SpringSecurityUtil;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
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
    private RegionRepository regionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private AttachService attachService;


    public ArticleInfoDTO create(ArticleCreateDTO createDTO){

        //checking the region
        Optional<RegionEntity> optional = regionRepository.findByIdAndVisibleIsTrue(createDTO.getRegionId());
        if (optional.isEmpty()) {
            throw new AppBadException("Region not found");
        }

        // checking category
        for (CategoryDTO categoryDTO : createDTO.getCategories()) {
            Optional<CategoryEntity> categoryOptional = categoryRepository.findByIdAndVisibleIsTrue(categoryDTO.getId());
            if (categoryOptional.isEmpty()) {
                throw new AppBadException("Category " + categoryDTO.getId() + " not found");
            }
        }

        // checking section
        for (SectionDTO sectionDTO : createDTO.getSections()) {
            Optional<SectionEntity> sectionOptional = sectionRepository.findByIdAndVisibleIsTrue(sectionDTO.getId());
            if (sectionOptional.isEmpty()) {
                throw new AppBadException("Section " + sectionDTO.getId() + " not found");
            }
        }

        // Check if the image exists in the attachment table
        if (createDTO.getImageId() != null && !createDTO.getImageId().isEmpty()) {
            boolean imageExists = attachService.isImageExists(createDTO.getImageId());
            if (!imageExists) {
                throw new AppBadException("Image with ID '" + createDTO.getImageId() + "' not found. Please upload the image first.");
            }
        }

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
        entity.setReadTime(createDTO.getReadTime());

        // setting moderator id
        entity.setModeratorId(SpringSecurityUtil.currentProfileId());

        repository.save(entity);

        // saving categories
        articleCategoryService.merge(entity.getId(), createDTO.getCategories());

        // saving sections
        articleSectionService.merge(entity.getId(), createDTO.getSections());

        return toArticleInfoDTO(entity);
    }

    public ArticleInfoDTO update(Integer id, ArticleUpdateDTO updateDTO){
        // checking article existence
        Optional<ArticleEntity> optional = repository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadException("Article not found");
        }

        // checking region
        Optional<RegionEntity> regionOptinal = regionRepository.findByIdAndVisibleIsTrue(updateDTO.getRegionId());
        if (regionOptinal.isEmpty()) {
            throw new AppBadException("Region not found");
        }

        // checking category
        for (CategoryDTO categoryDTO : updateDTO.getCategories()) {
            Optional<CategoryEntity> categoryOptional = categoryRepository.findByIdAndVisibleIsTrue(categoryDTO.getId());
            if (categoryOptional.isEmpty()) {
                throw new AppBadException("Category " + categoryDTO.getId() + " not found");
            }
        }

        // checking section
        for (SectionDTO sectionDTO : updateDTO.getSections()) {
            Optional<SectionEntity> sectionOptional = sectionRepository.findByIdAndVisibleIsTrue(sectionDTO.getId());
            if (sectionOptional.isEmpty()) {
                throw new AppBadException("Section " + sectionDTO.getId() + " not found");
            }
        }

        ArticleEntity entity = optional.get();

        // basic properties
        entity.setTitle(updateDTO.getTitle());
        entity.setDescription(updateDTO.getDescription());
        entity.setContent(updateDTO.getContent());
        entity.setStatus(ArticleStatusEnum.NOT_PUBLISHED);
        entity.setReadTime(updateDTO.getReadTime());

        // remove image and update image if changed
        if (!entity.getImageId().equals(updateDTO.getImageId())) {
            boolean imageExists = attachService.isImageExists(updateDTO.getImageId());
            if (!imageExists) {
                throw new AppBadException("Image not exists");
            }
            attachService.delete(entity.getImageId());
        }

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

        // delete image
        attachService.delete(optional.get().getImageId());

        repository.delete(optional.get());
        return "Article deleted successfully";
    }

    public String changeStatus(ArticleChangeStatusDTO dto) {
        Optional<ArticleEntity> optional = repository.findById(dto.getId());
        if (optional.isEmpty()) {
            throw new AppBadException("Article not found");
        }
        repository.changeStatusById(dto.getStatus(), dto.getId());
        return "Success";
    }

    public Page<ArticleInfoDTO> getArticlesBySection(int sectionId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ArticleEntity> entities = repository.getArticleBySectionId(sectionId, pageable);

        List<ArticleEntity> entityList = entities.getContent();
        long totalElement = entities.getTotalElements();

        List<ArticleInfoDTO> dtos = new LinkedList<>();
        entityList.forEach( entity -> dtos.add(toArticleInfoDTO(entity)));

        return new PageImpl<>(dtos, pageable, totalElement);
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
