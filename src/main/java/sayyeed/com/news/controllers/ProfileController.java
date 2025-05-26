package sayyeed.com.news.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sayyeed.com.news.dtos.profile.CreateProfileDTO;
import sayyeed.com.news.dtos.profile.ProfileInfoDTO;
import sayyeed.com.news.dtos.profile.UpdateProfileDto;
import sayyeed.com.news.services.profile.ProfileService;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private ProfileService service;

    @PostMapping("")
    public ResponseEntity<ProfileInfoDTO> create(@RequestBody @Valid CreateProfileDTO dto){
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfileInfoDTO> update(@PathVariable Integer id,@Valid @RequestBody UpdateProfileDto dto){
        return ResponseEntity.ok(service.update(id, dto));
    }
}
