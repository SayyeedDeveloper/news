package sayyeed.com.news.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sayyeed.com.news.DTOs.SectionDTO;
import sayyeed.com.news.Entities.SectionEntity;
import sayyeed.com.news.Exceptions.AppBadException;
import sayyeed.com.news.Exceptions.NotFoundException;
import sayyeed.com.news.Repositories.SectionRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SectionService {

    @Autowired
    private SectionRepository repository;

    public SectionDTO create(SectionDTO dto){
        try {
            SectionEntity entity = new SectionEntity();
            entity.setOrderNumber(dto.getOrderNumber());
            entity.setNameUz(dto.getNameUz());
            entity.setNameRu(dto.getNameRu());
            entity.setNameEn(dto.getNameEn());
            entity.setSectionKey(dto.getSectionKey());
            entity.setCreatedDate(LocalDateTime.now());
            entity.setImageId(dto.getImageId());
            repository.save(entity);
            dto.setId(entity.getId());
            dto.setCreatedDate(entity.getCreatedDate());
            return dto;
        }catch (Exception e){
            if(e.getMessage().contains("unique constraint") || e.getMessage().contains("Duplicate entry")){
                throw new AppBadException("Category with order number " + dto.getOrderNumber() + " already exists");
            }
            throw e;
        }
    }

    public SectionDTO update(Integer id, SectionDTO newDto){
        Optional<SectionEntity> optional = repository.findById(id);
        if (optional.isEmpty() || optional.get().getVisible() == Boolean.FALSE){
            throw new NotFoundException("Category not found");
        }
        SectionEntity entity = optional.get();
        entity.setOrderNumber(newDto.getOrderNumber());
        entity.setNameUz(newDto.getNameUz());
        entity.setNameRu(newDto.getNameRu());
        entity.setNameEn(newDto.getNameEn());
        entity.setSectionKey(newDto.getSectionKey());
        newDto.setId(entity.getId());
        newDto.setCreatedDate(entity.getCreatedDate());
        newDto.setImageId(entity.getImageId());
        repository.save(entity);
        return newDto;
    }

    public Boolean delete(Integer id) {
        var entity = repository.findByIdAndVisibleIsTrue(id)
                .orElseThrow(() -> new NotFoundException("Category not found"));
        int i = repository.updateVisibleById(entity.getId());
        return i == 1;
    }
}
