package sayyeed.com.news.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sayyeed.com.news.dtos.article.ArticleCreateDTO;
import sayyeed.com.news.dtos.article.ArticleInfoDTO;
import sayyeed.com.news.services.article.ArticleService;

@RestController
@RequestMapping("/api/article")
public class ArticleController {

    @Autowired
    private ArticleService service;

    @PostMapping("")
    public ResponseEntity<ArticleInfoDTO> create(@RequestBody ArticleCreateDTO createDTO){
        return ResponseEntity.ok(service.create(createDTO));
    }
}
