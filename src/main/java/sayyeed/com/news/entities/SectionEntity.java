package sayyeed.com.news.entities;

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

    @Column
    private Integer orderNumber;

    @Column
    private String nameUz;

    @Column
    private String nameRu;

    @Column
    private String nameEn;

    @Column()
    private String sectionKey;

    @Column
    private Boolean visible = true;

    @Column
    private LocalDateTime createdDate;

    @Column
    private String imageId;

}
