package sayyeed.com.news.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sayyeed.com.news.dtos.LangResponseDTO;
import sayyeed.com.news.dtos.SectionDTO;
import sayyeed.com.news.entities.SectionEntity;
import sayyeed.com.news.enums.AppLanguageEnum;
import sayyeed.com.news.exceptions.AppBadException;
import sayyeed.com.news.repositories.SectionRepository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class SectionService {

    @Autowired
    private SectionRepository repository;

    public SectionDTO create(SectionDTO dto){
        Optional<SectionEntity> optional = repository.findBySectionKeyAndVisibleIsTrue(dto.getSectionKey());
        if (optional.isPresent()) {
            throw new AppBadException("Section key " + dto.getSectionKey() + " already exist");
        }
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
    }

    public SectionDTO update(Integer id, SectionDTO newDto){
        Optional<SectionEntity> optional = repository.findByIdAndVisibleIsTrue(id);
        if (optional.isEmpty()){
            throw new AppBadException("Section not found");
        }

        Optional<SectionEntity> keyOptional = repository.findBySectionKeyAndVisibleIsTrue(newDto.getSectionKey());
        if (keyOptional.isPresent() && !keyOptional.get().getId().equals(id)){
            throw new AppBadException("Section key present");
        }

        SectionEntity entity = optional.get();
        entity.setOrderNumber(newDto.getOrderNumber());
        entity.setNameUz(newDto.getNameUz());
        entity.setNameRu(newDto.getNameRu());
        entity.setNameEn(newDto.getNameEn());
        entity.setSectionKey(newDto.getSectionKey());
        entity.setImageId(newDto.getImageId());
        repository.save(entity);
        //response
        newDto.setId(entity.getId());
        return newDto;
    }

    public Boolean delete(Integer id) {
        return repository.updateVisibleById(id) == 1;
    }

    public List<SectionDTO> getAllByOrder() {
        Iterable<SectionEntity> iterable = repository.getAllByOrderSorted();
        List<SectionDTO> dtos = new LinkedList<>();
        iterable.forEach(entity -> dtos.add(toDto(entity)));
        return dtos;
    }

    public List<LangResponseDTO> getAllByLang(AppLanguageEnum lang){
        Iterable<SectionEntity> iterable = repository.getAllByOrderSorted();
        List<LangResponseDTO> dtos = new LinkedList<>();
        iterable.forEach(entity -> dtos.add(toLangResponseDto(lang, entity)));
        return dtos;
    }

    public List<Integer> getAllSectionIds() {
        return repository.getAllSectionIds();
    }

    private SectionDTO toDto(SectionEntity entity) {
        SectionDTO dto = new SectionDTO();
        dto.setId(entity.getId());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        dto.setSectionKey(entity.getSectionKey());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setImageId(entity.getImageId());
        return dto;
    }

    private LangResponseDTO toLangResponseDto(AppLanguageEnum lang, SectionEntity entity){
        LangResponseDTO dto = new LangResponseDTO();
        dto.setId(entity.getId());
        dto.setKey(entity.getSectionKey());
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
