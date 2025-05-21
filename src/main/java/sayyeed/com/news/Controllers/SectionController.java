package sayyeed.com.news.Controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sayyeed.com.news.DTOs.SectionDTO;
import sayyeed.com.news.Services.SectionService;

@RestController
@RequestMapping("/api/section")
public class SectionController {
    @Autowired
    private SectionService service;

    @PostMapping("")
    public ResponseEntity<SectionDTO> create(@Valid @RequestBody SectionDTO dto){
        return ResponseEntity.ok(service.create(dto));
    }
}
