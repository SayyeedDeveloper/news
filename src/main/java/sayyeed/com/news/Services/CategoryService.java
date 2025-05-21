package sayyeed.com.news.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sayyeed.com.news.DTOs.CategoryDTO;
import sayyeed.com.news.Entities.CategoryEntity;
import sayyeed.com.news.Entities.RegionEntity;
import sayyeed.com.news.Exceptions.AppBadException;
import sayyeed.com.news.Exceptions.NotFoundException;
import sayyeed.com.news.Repositories.CategoryRepository;

import java.time.LocalDateTime;
import java.util.Optional;

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
                throw new AppBadException("Category with order number " + dto.getOrderNumber() + " already exists");
            }
            throw e;
        }
    }

    public CategoryDTO update(Integer id, CategoryDTO newDto){
        Optional<CategoryEntity> optional = repository.findById(id);
        if (optional.isEmpty() || optional.get().getVisible() == Boolean.FALSE){
            throw new NotFoundException("Category not found");
        }
        CategoryEntity entity = optional.get();
        entity.setOrder_number(newDto.getOrderNumber());
        entity.setNameUz(newDto.getNameUz());
        entity.setNameRu(newDto.getNameRu());
        entity.setNameEn(newDto.getNameEn());
        entity.setCategoryKey(newDto.getCategoryKey());
        newDto.setId(entity.getId());
        newDto.setCreatedDate(entity.getCreated_date());
        repository.save(entity);
        return newDto;
    }

    public Boolean delete(Integer id){
        Optional<CategoryEntity> optional = repository.findById(id);
        if (optional.isEmpty() || optional.get().getVisible() == Boolean.FALSE){
            throw new NotFoundException("Category not found");
        }
        CategoryEntity entity = optional.get();
        entity.setVisible(Boolean.FALSE);
        repository.save(entity);
        return Boolean.TRUE;
    }
}
