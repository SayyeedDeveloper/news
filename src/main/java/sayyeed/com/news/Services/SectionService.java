package sayyeed.com.news.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sayyeed.com.news.DTOs.SectionDTO;
import sayyeed.com.news.Entities.SectionEntity;
import sayyeed.com.news.Exceptions.AppBadException;
import sayyeed.com.news.Repositories.SectionRepository;

import java.time.LocalDateTime;

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
}
