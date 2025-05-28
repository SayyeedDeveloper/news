package sayyeed.com.news.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sayyeed.com.news.dtos.profile.ProfileCreateDTO;
import sayyeed.com.news.dtos.profile.ProfileFilterDTO;
import sayyeed.com.news.dtos.profile.ProfileInfoDTO;
import sayyeed.com.news.dtos.profile.ProfileUpdateDTO;
import sayyeed.com.news.services.profile.ProfileService;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private ProfileService service;

    @PostMapping("")
    public ResponseEntity<ProfileInfoDTO> create(@RequestBody @Valid ProfileCreateDTO dto){
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfileInfoDTO> update(@PathVariable Integer id,@Valid @RequestBody ProfileUpdateDTO dto){
        return ResponseEntity.ok(service.update(id, dto));
    }

    @GetMapping("")
    public ResponseEntity<Page<ProfileInfoDTO>> getAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(service.getAll(page, size));
    }

    @PostMapping("/filter")
    public ResponseEntity<Page<ProfileInfoDTO>> filter(
            @RequestBody ProfileFilterDTO filterDTO,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok(service.filter(filterDTO, page - 1, size));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id){
        return ResponseEntity.ok(service.delete(id));
    }


    //TODO Delete profile by Id
}
