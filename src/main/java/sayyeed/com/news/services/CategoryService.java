package sayyeed.com.news.services;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sayyeed.com.news.dtos.CategoryDTO;
import sayyeed.com.news.dtos.LangResponseDTO;
import sayyeed.com.news.entities.CategoryEntity;
import sayyeed.com.news.exceptions.AppBadException;
import sayyeed.com.news.exceptions.NotFoundException;
import sayyeed.com.news.repositories.CategoryRepository;

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

    public CategoryDTO create(CategoryDTO dto) {
        Optional<CategoryEntity> optional = repository.findByOrderNumber(dto.getOrderNumber());
        if (optional.isPresent()) {
            throw new AppBadException("OrderNumber " + dto.getOrderNumber() + " already exist");
        }
        CategoryEntity entity = new CategoryEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setCategoryKey(dto.getCategoryKey());
        entity.setCreatedDate(LocalDateTime.now());
        repository.save(entity);
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public CategoryDTO update(Integer id, CategoryDTO newDto) {
        Optional<CategoryEntity> optional = repository.findById(id);
        if (optional.isEmpty() || optional.get().getVisible() == Boolean.FALSE) {
            throw new NotFoundException("Category not found");
        }
        CategoryEntity entity = optional.get();
        entity.setOrderNumber(newDto.getOrderNumber());
        entity.setNameUz(newDto.getNameUz());
        entity.setNameRu(newDto.getNameRu());
        entity.setNameEn(newDto.getNameEn());
        entity.setCategoryKey(newDto.getCategoryKey());
        newDto.setId(entity.getId());
        newDto.setCreatedDate(entity.getCreatedDate());
        repository.save(entity);
        return newDto;
    }

    public Boolean delete(Integer id) {
        var entity = repository.findByIdAndVisibleIsTrue(id)
                .orElseThrow(() -> new NotFoundException("Category not found"));
        int i = repository.updateVisibleById(entity.getId());
        return i == 1;
    }

    public List<CategoryDTO> getAllByOrder() {
        Iterable<CategoryEntity> iterable = repository.getAllByOrderSorted();
        List<CategoryDTO> dtos = new LinkedList<>();
        iterable.forEach(entity -> dtos.add(toDto(entity)));
        return dtos;
    }

    public List<LangResponseDTO> getAllbyLang(String lang) {
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

    private LangResponseDTO toLangResponseDto(String lang, CategoryEntity entity) {
        LangResponseDTO dto = new LangResponseDTO();
        dto.setId(entity.getId());
        dto.setKey(entity.getCategoryKey());
        switch (lang) {
            case "uz":
                dto.setName(entity.getNameUz());
                break;
            case "ru":
                dto.setName(entity.getNameRu());
                break;
            case "en":
                dto.setName(entity.getNameEn());
                break;
        }
        return dto;
    }
}
