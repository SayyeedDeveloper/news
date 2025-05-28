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

    public ArticleInfoDTO create(ArticleCreateDTO createDTO){
        ArticleEntity entity = new ArticleEntity();
        entity.setTitle(createDTO.getTitle());
        entity.setDescription(createDTO.getDescription());
        entity.setContent(createDTO.getContent());
        entity.setImageId(createDTO.getImageId());
        entity.setRegionId(createDTO.getRegionId());
        entity.setStatus(ArticleStatusEnum.NOT_PUBLISHED);
        entity.setCreatedDate(LocalDateTime.now());
//        entity.setCategorySet(createDTO.getCategories());

        return null;
    }
}
