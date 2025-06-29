package sayyeed.com.news.services.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sayyeed.com.news.dtos.CategoryDTO;
import sayyeed.com.news.dtos.article.ArticleInfoDTO;
import sayyeed.com.news.entities.article.ArticleCategoryEntity;
import sayyeed.com.news.exceptions.AppBadException;
import sayyeed.com.news.repositories.article.ArticleCategoryRepository;
import sayyeed.com.news.services.CategoryService;

import java.util.List;

@Service
public class ArticleCategoryService {

    @Autowired
    private ArticleCategoryRepository repository;

    @Autowired
    private CategoryService categoryService;

    public void merge(Integer articleId, List<CategoryDTO> dtoList){
        // changing DTO list into List of IDs
        List<Integer> newList = dtoList.stream().map(CategoryDTO::getId).toList();

        List<Integer> oldList = repository.getCategoryIdsByArticleId(articleId);
        newList.stream().filter(newId -> !oldList.contains(newId)).forEach( cId -> create(articleId, cId));
        oldList.stream().filter(oldId -> !newList.contains(oldId)).forEach(cId -> repository.deleteByArticleIdAndCategoryId(articleId, cId));
    }

    public void create(Integer articleId, Integer categoryId){
        ArticleCategoryEntity entity = new ArticleCategoryEntity();
        entity.setArticleId(articleId);
        entity.setCategoryId(categoryId);
        repository.save(entity);
    }

    public List<Integer> getCategoryIds(Integer articleId) {
        return repository.getCategoryIdsByArticleId(articleId);
    }

    public void deleteArticleCategoryByArticleId(Integer articleId) {
        repository.deleteArticleCategoryByArticleId(articleId);
    }

}
