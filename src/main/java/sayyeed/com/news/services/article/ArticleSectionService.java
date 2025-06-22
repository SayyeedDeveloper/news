package sayyeed.com.news.services.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sayyeed.com.news.dtos.CategoryDTO;
import sayyeed.com.news.dtos.SectionDTO;
import sayyeed.com.news.entities.article.ArticleSectionEntity;
import sayyeed.com.news.exceptions.AppBadException;
import sayyeed.com.news.repositories.article.ArticleRepository;
import sayyeed.com.news.repositories.article.ArticleSectionRepository;
import sayyeed.com.news.services.SectionService;

import java.util.List;

@Service
public class ArticleSectionService {

    @Autowired
    private ArticleSectionRepository repository;

    @Autowired
    private SectionService sectionService;

    public void merge(Integer articleId, List<SectionDTO> dtoList){
        // changing DTO list into List of IDs
        List<Integer> newList = dtoList.stream().map(SectionDTO::getId).toList();

        List<Integer> oldSectionIds = repository.getSectionIdsByArticleId(articleId);
        newList.stream().filter(newId -> !oldSectionIds.contains(newId)).forEach(sId -> create(articleId, sId));
        oldSectionIds.stream().filter( oldId -> !newList.contains(oldId)).forEach( sId -> repository.deleteByArticleIdAndSectionId(articleId, sId));
    }

    private void create(Integer articleId, Integer sectionId){
        ArticleSectionEntity entity = new ArticleSectionEntity();
        entity.setArticleId(articleId);
        entity.setSectionId(sectionId);
        repository.save(entity);
    }

    public List<Integer> getSectionIdsByArticleId(Integer articleId) {
        return repository.getSectionIdsByArticleId(articleId);
    }

    public void deleteArticleSectionByArticleId(Integer articleId) {
        repository.deleteArticleSectionByArticleId(articleId);
    }
}
