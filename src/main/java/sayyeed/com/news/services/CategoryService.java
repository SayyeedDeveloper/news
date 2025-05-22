package sayyeed.com.news.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sayyeed.com.news.dtos.CategoryDTO;
import sayyeed.com.news.dtos.LangResponseDTO;
import sayyeed.com.news.entities.CategoryEntity;
import sayyeed.com.news.enums.AppLanguageEnum;
import sayyeed.com.news.exceptions.AppBadException;
import sayyeed.com.news.repositories.CategoryRepository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public CategoryDTO create(CategoryDTO dto) {
        Optional<CategoryEntity> optional = repository.findByCategoryKeyAndVisibleIsTrue(dto.getCategoryKey());
        if (optional.isPresent()) {
            throw new AppBadException("CategoryKey " + dto.getCategoryKey() + " already exist");
        }
        CategoryEntity entity = new CategoryEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setCategoryKey(dto.getCategoryKey());
        entity.setCreatedDate(LocalDateTime.now());
        repository.save(entity);
        //response
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public CategoryDTO update(Integer id, CategoryDTO newDto) {
        Optional<CategoryEntity> optional = repository.findByIdAndVisibleIsTrue(id);
        if (optional.isEmpty()) {
            throw new AppBadException("Category not found");
        }

        Optional<CategoryEntity> keyOptional = repository.findByCategoryKeyAndVisibleIsTrue(newDto.getCategoryKey());
        if (keyOptional.isPresent() && !id.equals(keyOptional.get().getId())) {
            throw new AppBadException("Category key present");
        }
        CategoryEntity entity = optional.get();
        entity.setOrderNumber(newDto.getOrderNumber());
        entity.setNameUz(newDto.getNameUz());
        entity.setNameRu(newDto.getNameRu());
        entity.setNameEn(newDto.getNameEn());
        entity.setCategoryKey(newDto.getCategoryKey());
        repository.save(entity);

        newDto.setId(entity.getId());
        return newDto;
    }

    public Boolean delete(Integer id) {
        return repository.updateVisibleById(id) == 1;
    }

    public List<CategoryDTO> getAllByOrder() {
        Iterable<CategoryEntity> iterable = repository.getAllByOrderSorted();
        List<CategoryDTO> dtos = new LinkedList<>();
        iterable.forEach(entity -> dtos.add(toDto(entity)));
        return dtos;
    }

    public List<LangResponseDTO> getAllByLang(AppLanguageEnum lang) {
        Iterable<CategoryEntity> iterable = repository.getAllByOrderSorted();
        List<LangResponseDTO> dtos = new LinkedList<>();
        iterable.forEach(entity -> dtos.add(toLangResponseDto(lang, entity)));
        return dtos;
    }

    private CategoryDTO toDto(CategoryEntity entity) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(entity.getId());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        dto.setCategoryKey(entity.getCategoryKey());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    private LangResponseDTO toLangResponseDto(AppLanguageEnum lang, CategoryEntity entity) {
        LangResponseDTO dto = new LangResponseDTO();
        dto.setId(entity.getId());
        dto.setKey(entity.getCategoryKey());
        switch (lang) {
            case UZ:
                dto.setName(entity.getNameUz());
                break;
            case RU:
                dto.setName(entity.getNameRu());
                break;
            case EN:
                dto.setName(entity.getNameEn());
                break;
        }
        return dto;
    }
}
