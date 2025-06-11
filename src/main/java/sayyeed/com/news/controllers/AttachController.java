package sayyeed.com.news.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sayyeed.com.news.dtos.AttachDTO;
import sayyeed.com.news.services.AttachService;

@RestController
@RequestMapping("/attach")
public class AttachController {
    @Autowired
    private AttachService attachService;


    @PostMapping("/upload")
    public ResponseEntity<AttachDTO> create(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(attachService.upload(file));
    }


    @GetMapping("/open/{fileId}")
    public ResponseEntity<Resource> open(@PathVariable String fileId) {
        return attachService.open(fileId);
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> download(@PathVariable String fileId) {
        return attachService.download(fileId);
    }

    @DeleteMapping("/delete/{fileId}")
    public ResponseEntity<Boolean> delete(@PathVariable String fileId) {
        return ResponseEntity.ok(attachService.delete(fileId));
    }

    @GetMapping("")
    public ResponseEntity<Page<AttachDTO>> getAll(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(attachService.getAll(page - 1, size));
    }
}

