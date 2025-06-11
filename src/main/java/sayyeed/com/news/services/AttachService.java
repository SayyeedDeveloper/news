package sayyeed.com.news.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sayyeed.com.news.dtos.AttachDTO;
import sayyeed.com.news.entities.AttachEntity;
import sayyeed.com.news.exceptions.AppBadException;
import sayyeed.com.news.repositories.AttachRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class AttachService {

    @Autowired
    private AttachRepository attachRepository;

    @Value("${app.server.name}")
    private String appName;

    @Value("${app.attaches.name}")
    private String attachesName;

    @Value("${app.attach.url}")
    private String attachUrl;

    public AttachDTO upload(MultipartFile file) {
        if (file.isEmpty()) {
            throw new AppBadException("File not found");
        }

        try {
            String pathFolder = getYmDString(); // 2025/06/09
            String key = UUID.randomUUID().toString(); // dasdasd-dasdasda-asdasda-asdasd
            String extension = getExtension(Objects.requireNonNull(file.getOriginalFilename())); // .jpg, .png, .mp4

            // create folder if not exists
            File folder = new File(attachesName + "/" + pathFolder);
            if (!folder.exists()) {
                boolean t = folder.mkdirs();
            }

            // save to system
            byte[] bytes = file.getBytes();
            Path path = Paths.get(attachesName + "/" + pathFolder + "/" + key + "." + extension);
            // attaches/ 2025/06/09/dasdasd-dasdasda-asdasda-asdasd.jpg
            Files.write(path, bytes);

            // save to db
            AttachEntity entity = new AttachEntity();
            entity.setId(key + "." + extension);
            entity.setPath(pathFolder);
            entity.setSize(file.getSize());
            entity.setOrigenName(file.getOriginalFilename());
            entity.setExtension(extension);
            entity.setVisible(true);
            attachRepository.save(entity);

            return toDTO(entity);
        } catch (IOException e) {
            throw new AppBadException("Upload went wrong");
        }
    }

    public ResponseEntity<Resource> open(String id) { // d5ab71b2-39a8-4ad2-80b3-729c91c932be.jpg
        AttachEntity entity = getEntity(id);
        Path filePath = Paths.get(attachesName + "/" + entity.getPath() + "/" + entity.getId()).normalize();
        // attaches/2025/06/09/d5ab71b2-39a8-4ad2-80b3-729c91c932be.jpg
        Resource resource = null;
        try {
            resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) {
                throw new RuntimeException("File not found: " + filePath);
            }
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream"; // Fallback content type
            }
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    public ResponseEntity<Resource> download(String filename) {
        AttachEntity entity = getEntity(filename);
        try {
            Path file = Paths.get(attachesName + "/" + entity.getPath() + "/" + entity.getId()).normalize();
            Resource resource = new UrlResource(file.toUri());

            if (!resource.exists()) {
                throw new AppBadException("File not found");
            }
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + entity.getOrigenName() + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (Exception e) {
            throw new AppBadException("Something went wrong");
        }
    }

    public Boolean delete(String fileId) {
        AttachEntity entity = getEntity(fileId);

        try {
            Path file = Paths.get(attachesName + "/" + entity.getPath() + "/" + entity.getId()).normalize();
            boolean deleted = Files.deleteIfExists(file);

            if (!deleted) {
                throw new AppBadException("Could not delete file from filesystem");
            }

            attachRepository.delete(entity);
            return true;

        } catch (Exception e) {
            throw new AppBadException("Something went wrong");
        }
    }

    public Page<AttachDTO> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AttachEntity> entities = attachRepository.findAllByOrderByCreatedDateDesc(pageable);

        List<AttachEntity> entityList = entities.getContent();
        long totalElement = entities.getTotalElements();

        List<AttachDTO> dtos = new LinkedList<>();
        entityList.forEach( e -> dtos.add(toDTO(e)));
        return new PageImpl<>(dtos, pageable, totalElement);
    }

    private String getYmDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);
        return year + "/" + month + "/" + day;
    }

    private String getExtension(String fileName) { // something.jpg
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }

    private AttachDTO toDTO(AttachEntity entity) {
        AttachDTO attachDTO = new AttachDTO();
        attachDTO.setId(entity.getId());
        attachDTO.setOriginName(entity.getOrigenName());
        attachDTO.setSize(entity.getSize());
        attachDTO.setExtension(entity.getExtension());
        attachDTO.setCreatedData(entity.getCreatedDate());
        attachDTO.setUrl(openURL(entity.getId()));
        return attachDTO;
    }

    public AttachEntity getEntity(String id) {
        return attachRepository.findById(id).orElseThrow(() -> new AppBadException("File not found"));
    }

    public String openURL(String fileName) {
        System.out.println(appName);
        return appName + "/" + attachUrl + "/open/" + fileName;
    }
}
