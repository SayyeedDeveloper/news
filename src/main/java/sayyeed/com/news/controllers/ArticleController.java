package sayyeed.com.news.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sayyeed.com.news.dtos.article.ArticleCreateDTO;
import sayyeed.com.news.dtos.article.ArticleInfoDTO;
import sayyeed.com.news.dtos.article.ArticleUpdateDTO;
import sayyeed.com.news.services.article.ArticleService;

@RestController
@RequestMapping("/api/article")
public class ArticleController {

    @Autowired
    private ArticleService service;

    @PostMapping("")
    public ResponseEntity<ArticleInfoDTO> create(@Valid @RequestBody ArticleCreateDTO createDTO) {
        return ResponseEntity.ok(service.create(createDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleInfoDTO> update(@Valid @RequestBody ArticleUpdateDTO updateDTO, @PathVariable Integer id) {
        return ResponseEntity.ok(service.update(id, updateDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(service.deleteArticle(id));
    }
}
