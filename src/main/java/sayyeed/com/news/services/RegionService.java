package sayyeed.com.news.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sayyeed.com.news.dtos.RegionDTO;
import sayyeed.com.news.dtos.LangResponseDTO;
import sayyeed.com.news.entities.RegionEntity;
import sayyeed.com.news.enums.AppLanguageEnum;
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
        Optional<RegionEntity> byOrderNumber = repository.findByRegionKeyAndVisibleIsTrue(dto.getRegionKey());
        if (byOrderNumber.isPresent()) {
            throw new AppBadException("Region key " + dto.getRegionKey() + " already exist");
        }
        RegionEntity entity = new RegionEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setRegionKey(dto.getRegionKey());
        entity.setCreatedDate(LocalDateTime.now());
        //response
        repository.save(entity);
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public RegionDTO update(Integer id, RegionDTO newDto){
        Optional<RegionEntity> optional = repository.findByIdAndVisibleIsTrue(id);
        if (optional.isEmpty()){
            throw new AppBadException("Region not found");
        }
        Optional<RegionEntity> keyOptional = repository.findByRegionKeyAndVisibleIsTrue(newDto.getRegionKey());
        if (keyOptional.isPresent() && !id.equals(keyOptional.get().getId())){
            throw new AppBadException("Region key present");
        }
        RegionEntity entity = optional.get();
        entity.setOrderNumber(newDto.getOrderNumber());
        entity.setNameUz(newDto.getNameUz());
        entity.setNameRu(newDto.getNameRu());
        entity.setNameEn(newDto.getNameEn());
        entity.setRegionKey(newDto.getRegionKey());
        repository.save(entity);
        //response
        newDto.setId(entity.getId());
        return newDto;
    }

    public Boolean delete(Integer id){
        return repository.updateVisibleById(id) == 1;
    }

    public List<RegionDTO> getAll(){
        Iterable<RegionEntity> iterable = repository.findAllByOrderNumberSorted();
        List<RegionDTO> dtos = new LinkedList<>();
        iterable.forEach(entity -> dtos.add(toDto(entity)));
        return dtos;
    }

    public List<LangResponseDTO> getAllByLang(AppLanguageEnum lang){
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

    private LangResponseDTO toLangResponseDto(AppLanguageEnum lang, RegionEntity entity){
        LangResponseDTO dto = new LangResponseDTO();
        dto.setId(entity.getId());
        dto.setKey(entity.getRegionKey());
        switch (lang){
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
