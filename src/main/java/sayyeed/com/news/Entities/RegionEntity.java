package sayyeed.com.news.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "Region")
public class RegionEntity {

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
    private Integer regionKey;

    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean visible = true;

    @Column
    private LocalDateTime created_date;

}
