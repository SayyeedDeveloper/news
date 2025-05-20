package sayyeed.com.news.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sayyeed.com.news.DTOs.RegionDTO;
import sayyeed.com.news.Entities.RegionEntity;
import sayyeed.com.news.Repositories.RegionRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RegionService {

    @Autowired
    private RegionRepository repository;

    public RegionDTO create(RegionDTO dto){
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
    }

    public RegionDTO update(Integer id, RegionDTO newDto){
        Optional<RegionEntity> optional = repository.findById(id);
        if (optional.isEmpty()){
            return null;
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
}
