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
@RequestMapping("/api/v1/article")
public class ArticleController {

    @Autowired
    private ArticleService service;

    @PostMapping("/moderator")
    public ResponseEntity<ArticleInfoDTO> create(
            @Valid @RequestBody ArticleCreateDTO createDTO,
            @RequestHeader(name = "Authorization") String token) {
        return ResponseEntity.ok(service.create(createDTO, token));
    }

    @PutMapping("/moderator/{id}")
    public ResponseEntity<ArticleInfoDTO> update(@Valid @RequestBody ArticleUpdateDTO updateDTO, @PathVariable Integer id) {
        return ResponseEntity.ok(service.update(id, updateDTO));
    }

    @DeleteMapping("/moderator/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(service.deleteArticle(id));
    }
}
