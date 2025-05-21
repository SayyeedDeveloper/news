package sayyeed.com.news.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sayyeed.com.news.DTOs.CategoryDTO;
import sayyeed.com.news.Entities.CategoryEntity;
import sayyeed.com.news.Exceptions.AppBadException;
import sayyeed.com.news.Repositories.CategoryRepository;

import java.time.LocalDateTime;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public CategoryDTO create(CategoryDTO dto){
        try {
            CategoryEntity entity = new CategoryEntity();
            entity.setOrder_number(dto.getOrderNumber());
            entity.setNameUz(dto.getNameUz());
            entity.setNameRu(dto.getNameRu());
            entity.setNameEn(dto.getNameEn());
            entity.setCategoryKey(dto.getCategoryKey());
            entity.setCreated_date(LocalDateTime.now());
            repository.save(entity);
            dto.setId(entity.getId());
            dto.setCreatedDate(entity.getCreated_date());
            return dto;
        }catch (Exception e){
            if(e.getMessage().contains("unique constraint") || e.getMessage().contains("Duplicate entry")){
                throw new AppBadException("Region with order number " + dto.getOrderNumber() + " already exists");
            }
            throw e;
        }
    }
}
