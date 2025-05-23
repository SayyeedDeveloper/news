package sayyeed.com.news.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sayyeed.com.news.dtos.profile.ProfileDTO;
import sayyeed.com.news.dtos.profile.ProfileInfoDTO;
import sayyeed.com.news.services.ProfileService;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private ProfileService service;

    @PostMapping("")
    public ResponseEntity<ProfileInfoDTO> create(@RequestBody @Valid ProfileDTO dto){
        return ResponseEntity.ok(service.create(dto));
    }
}
