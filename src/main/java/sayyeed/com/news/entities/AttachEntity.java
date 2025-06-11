package sayyeed.com.news.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "attach")
@Getter
@Setter
public class AttachEntity {
    @Id
    private String id;

    @Column(name = "path")
    private String path;

    @Column(name = "extension")
    private String extension;

    @Column(name = "origen_name")
    private String origenName;

    @Column(name = "size")
    private Long size;

    @Column(name = "created_date")
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column(name = "visible")
    private Boolean visible = true;
}

