package sayyeed.com.news.services.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public void merge(Integer articleId, List<Integer> newSectionIds){

        List<Integer> availableSections = sectionService.getAllSectionIds();

        for (Integer id: newSectionIds) {
            if (!availableSections.contains(id)) {
                throw new AppBadException("Section " + id + " not found");
            }
        }

        List<Integer> oldSectionIds = repository.getSectionIdsByArticleId(articleId);
        newSectionIds.stream().filter(newId -> !oldSectionIds.contains(newId)).forEach(sId -> create(articleId, sId));
        oldSectionIds.stream().filter( oldId -> !newSectionIds.contains(oldId)).forEach( sId -> repository.deleteByArticleIdAndSectionId(articleId, sId));
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
