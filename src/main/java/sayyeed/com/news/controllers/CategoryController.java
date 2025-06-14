package sayyeed.com.news.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sayyeed.com.news.dtos.CategoryDTO;
import sayyeed.com.news.dtos.LangResponseDTO;
import sayyeed.com.news.enums.AppLanguageEnum;
import sayyeed.com.news.services.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @PostMapping("/admin")
    public ResponseEntity<CategoryDTO> create(@Valid @RequestBody CategoryDTO dto){
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable("id") Integer id, @RequestBody CategoryDTO newDto){
        return ResponseEntity.ok(service.update(id, newDto));
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id){
        return ResponseEntity.ok(service.delete(id));
    }

    @GetMapping("/admin")
    public ResponseEntity<List<CategoryDTO>> getAllByOrder(){
        return ResponseEntity.ok(service.getAllByOrder());
    }

    @GetMapping("/lang")
    public ResponseEntity<List<LangResponseDTO>> getByLang(@RequestHeader(name = "Accept-Language", defaultValue = "UZ") AppLanguageEnum language) {
        return ResponseEntity.ok(service.getAllByLang(language));
    }
}
