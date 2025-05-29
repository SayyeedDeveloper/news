package sayyeed.com.news.services.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public void merge(Integer articleId, List<Integer> categoryIds){

        List<Integer> allAvailableCategories = categoryService.getAllCategoryIds();

        for (Integer id: categoryIds){
            if (!allAvailableCategories.contains(id)){
                throw new AppBadException("Category " + id + " not Found");
            }
        }

        List<Integer> oldList = repository.getCategoryIdsByArticleId(articleId);
        categoryIds.stream().filter(newId -> !oldList.contains(newId)).forEach( cId -> create(articleId, cId));
        oldList.stream().filter(oldId -> !categoryIds.contains(oldId)).forEach(cId -> repository.deleteByArticleIdAndCategoryId(articleId, cId));
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

}
