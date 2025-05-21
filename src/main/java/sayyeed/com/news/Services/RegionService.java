package sayyeed.com.news.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sayyeed.com.news.DTOs.RegionDTO;
import sayyeed.com.news.DTOs.LangResponseDTO;
import sayyeed.com.news.Entities.RegionEntity;
import sayyeed.com.news.Exceptions.AppBadException;
import sayyeed.com.news.Exceptions.NotFoundException;
import sayyeed.com.news.Repositories.RegionRepository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class RegionService {

    @Autowired
    private RegionRepository repository;

    public RegionDTO create(RegionDTO dto){
        try {
            RegionEntity entity = new RegionEntity();
            entity.setOrder_number(dto.getOrderNumber());
            entity.setNameUz(dto.getNameUz());
            entity.setNameRu(dto.getNameRu());
            entity.setNameEn(dto.getNameEn());
            entity.setRegionKey(dto.getRegionKey());
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

    public RegionDTO update(Integer id, RegionDTO newDto){
        Optional<RegionEntity> optional = repository.findById(id);
        if (optional.isEmpty() || optional.get().getVisible() == Boolean.FALSE){
            throw new NotFoundException("Region not found");
        }
        RegionEntity entity = optional.get();
        entity.setOrder_number(newDto.getOrderNumber());
        entity.setNameUz(newDto.getNameUz());
        entity.setNameRu(newDto.getNameRu());
        entity.setNameEn(newDto.getNameEn());
        entity.setRegionKey(newDto.getRegionKey());
        newDto.setId(entity.getId());
        newDto.setCreatedDate(entity.getCreated_date());
        repository.save(entity);
        return newDto;
    }

    public Boolean delete(Integer id){
        Optional<RegionEntity> optional = repository.findById(id);
        if (optional.isEmpty() || optional.get().getVisible() == Boolean.FALSE){
            throw new NotFoundException("Region not found");
        }
        RegionEntity entity = optional.get();
        entity.setVisible(Boolean.FALSE);
        repository.save(entity);
        return Boolean.TRUE;

    }

    public List<RegionDTO> getAll(){
        Iterable<RegionEntity> iterable = repository.findAllByOrder_numberSorted();
        List<RegionDTO> dtos = new LinkedList<>();
        iterable.forEach(entity -> dtos.add(toDto(entity)));
        return dtos;
    }

    public List<LangResponseDTO> getAllbyLang(String lang){
        Iterable<RegionEntity> iterable = repository.findAllByOrder_numberSorted();
        List<LangResponseDTO> dtos = new LinkedList<>();
        iterable.forEach(entity -> dtos.add(toLangResponseDto(lang, entity)));
        return dtos;
    }

    private RegionDTO toDto(RegionEntity entity){
        RegionDTO dto = new RegionDTO();
        dto.setId(entity.getId());
        dto.setOrderNumber(entity.getOrder_number());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        dto.setRegionKey(entity.getRegionKey());
        dto.setCreatedDate(entity.getCreated_date());
        return dto;
    }

    private LangResponseDTO toLangResponseDto(String lang, RegionEntity entity){
        LangResponseDTO dto = new LangResponseDTO();
        dto.setId(entity.getId());
        dto.setKey(entity.getRegionKey());
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
