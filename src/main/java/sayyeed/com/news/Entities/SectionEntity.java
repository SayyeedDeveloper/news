package sayyeed.com.news.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Section")
@Getter
@Setter
public class SectionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private Integer orderNumber;

    @Column
    private String nameUz;

    @Column
    private String nameRu;

    @Column
    private String nameEn;

    @Column
    private Integer SectionKey;

    @Column
    private Boolean visible = true;

    @Column
    private LocalDateTime createdDate;

    @Column
    private String imageId;

}
