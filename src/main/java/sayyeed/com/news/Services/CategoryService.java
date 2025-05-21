package sayyeed.com.news.Services;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sayyeed.com.news.DTOs.CategoryDTO;
import sayyeed.com.news.DTOs.LangResponseDTO;
import sayyeed.com.news.DTOs.RegionDTO;
import sayyeed.com.news.Entities.CategoryEntity;
import sayyeed.com.news.Entities.RegionEntity;
import sayyeed.com.news.Exceptions.AppBadException;
import sayyeed.com.news.Exceptions.NotFoundException;
import sayyeed.com.news.Repositories.CategoryRepository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;
    @Autowired
    private EntityManagerFactory entityManagerFactory;

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

    public List<CategoryDTO> getAllByOrder(){
        Iterable<CategoryEntity> iterable = repository.getAllByOrderSorted();
        List<CategoryDTO> dtos = new LinkedList<>();
        iterable.forEach( entity -> dtos.add(toDto(entity)));
        return dtos;
    }

    public List<LangResponseDTO> getAllbyLang(String lang){
        Iterable<CategoryEntity> iterable = repository.getAllByOrderSorted();
        List<LangResponseDTO> dtos = new LinkedList<>();
        iterable.forEach(entity -> dtos.add(toLangResponseDto(lang, entity)));
        return dtos;
    }

    private CategoryDTO toDto(CategoryEntity entity){
        CategoryDTO dto = new CategoryDTO();
        dto.setId(entity.getId());
        dto.setOrderNumber(entity.getOrder_number());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        dto.setCategoryKey(entity.getCategoryKey());
        dto.setCreatedDate(entity.getCreated_date());
        return dto;
    }

    private LangResponseDTO toLangResponseDto(String lang, CategoryEntity entity){
        LangResponseDTO dto = new LangResponseDTO();
        dto.setId(entity.getId());
        dto.setKey(entity.getCategoryKey());
        switch (lang){
            case "uz":
                dto.setName(entity.getNameUz());
                break;
            case"ru":
                dto.setName(entity.getNameRu());
                break;
            case "en":
                dto.setName(entity.getNameEn());
                break;
        }
        return dto;
    }
}
