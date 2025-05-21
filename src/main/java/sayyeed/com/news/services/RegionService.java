package sayyeed.com.news.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sayyeed.com.news.dtos.RegionDTO;
import sayyeed.com.news.dtos.LangResponseDTO;
import sayyeed.com.news.entities.RegionEntity;
import sayyeed.com.news.exceptions.AppBadException;
import sayyeed.com.news.exceptions.NotFoundException;
import sayyeed.com.news.repositories.RegionRepository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class RegionService {

    @Autowired
    private RegionRepository repository;

    public RegionDTO create(RegionDTO dto){
        Optional<RegionEntity> byOrderNumber = repository.findByOrderNumber(dto.getOrderNumber());
        if (byOrderNumber.isPresent()) {
            throw new AppBadException("OrderNumber " + dto.getOrderNumber() + " already exist");
        }
        RegionEntity entity = new RegionEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setRegionKey(dto.getRegionKey());
        entity.setCreatedDate(LocalDateTime.now());
        repository.save(entity);
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public RegionDTO update(Integer id, RegionDTO newDto){
        Optional<RegionEntity> optional = repository.findById(id);
        if (optional.isEmpty() || optional.get().getVisible() == Boolean.FALSE){
            throw new NotFoundException("Region not found");
        }
        RegionEntity entity = optional.get();
        entity.setOrderNumber(newDto.getOrderNumber());
        entity.setNameUz(newDto.getNameUz());
        entity.setNameRu(newDto.getNameRu());
        entity.setNameEn(newDto.getNameEn());
        entity.setRegionKey(newDto.getRegionKey());
        newDto.setId(entity.getId());
        newDto.setCreatedDate(entity.getCreatedDate());
        repository.save(entity);
        return newDto;
    }

    public Boolean delete(Integer id){
        var entity = repository.findByIdAndVisibleIsTrue(id)
                .orElseThrow(() -> new NotFoundException("Category not found"));
        int i = repository.updateVisibleById(entity.getId());
        return i == 1;
    }

    public List<RegionDTO> getAll(){
        Iterable<RegionEntity> iterable = repository.findAllByOrderNumberSorted();
        List<RegionDTO> dtos = new LinkedList<>();
        iterable.forEach(entity -> dtos.add(toDto(entity)));
        return dtos;
    }

    public List<LangResponseDTO> getAllbyLang(String lang){
        Iterable<RegionEntity> iterable = repository.findAllByOrderNumberSorted();
        List<LangResponseDTO> dtos = new LinkedList<>();
        iterable.forEach(entity -> dtos.add(toLangResponseDto(lang, entity)));
        return dtos;
    }

    private RegionDTO toDto(RegionEntity entity){
        RegionDTO dto = new RegionDTO();
        dto.setId(entity.getId());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        dto.setRegionKey(entity.getRegionKey());
        dto.setCreatedDate(entity.getCreatedDate());
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
