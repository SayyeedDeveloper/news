package sayyeed.com.news.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sayyeed.com.news.dtos.article.*;
import sayyeed.com.news.mapper.ArticleShortInfo;
import sayyeed.com.news.services.article.ArticleService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/article")
public class ArticleController {

    @Autowired
    private ArticleService service;

    @PostMapping("/moderator")
    public ResponseEntity<ArticleInfoDTO> create(@Valid @RequestBody ArticleCreateDTO createDTO) {
        return ResponseEntity.ok(service.create(createDTO));
    }

    @PutMapping("/moderator/{id}")
    public ResponseEntity<ArticleInfoDTO> update(@Valid @RequestBody ArticleUpdateDTO updateDTO, @PathVariable Integer id) {
        return ResponseEntity.ok(service.update(id, updateDTO));
    }

    @DeleteMapping("/moderator/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(service.deleteArticle(id));
    }

    @PutMapping("/publisher")
    public ResponseEntity<String> changeStatus(@Valid @RequestBody ArticleChangeStatusDTO dto) {
        return ResponseEntity.ok(service.changeStatus(dto));
    }

    @GetMapping("/section/{sectionId}")
    public ResponseEntity<List<ArticleInfoDTO>> getArticlesBySection(
            @PathVariable() Integer sectionId,
            @RequestParam(value = "limit", defaultValue = "5") int limit
    ) {
        return ResponseEntity.ok(service.getArticlesBySection(sectionId, limit));
    }

    @PostMapping("published/latest")
    public ResponseEntity<List<ArticleInfoDTO>> getLatestPublishedArticles(
            @RequestBody ArticleLastPublishedDTO dto,
            @RequestParam(value = "limit", defaultValue = "12") int limit
    ) {
        return ResponseEntity.ok(service.getLatestPublishedArticles(dto, limit));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ArticleInfoDTO>> getArticlesByCategory(
            @PathVariable() Integer categoryId,
            @RequestParam(value = "size", defaultValue = "5") int limit
    ) {
        return ResponseEntity.ok(service.getArticlesByCategory(categoryId, limit));
    }

    @GetMapping("/region/{regionId}")
    public ResponseEntity<Page<ArticleInfoDTO>> getArticlesByRegion(
            @PathVariable() Integer regionId,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int size
    ) {
        return ResponseEntity.ok(service.getArticlesByRegion(regionId, page - 1, size));
    }

    @PostMapping("/pulished/filter")
    public ResponseEntity<Page<ArticleShortInfo>> filter(
            @RequestBody ArticleFilterDTO dto,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "5") int size
            ) {
        return ResponseEntity.ok(service.filter(dto, page - 1, size));
    }

}
