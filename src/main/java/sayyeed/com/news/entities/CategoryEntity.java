package sayyeed.com.news.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sayyeed.com.news.entities.article.ArticleEntity;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "Category")
@Setter
@Getter
public class CategoryEntity {
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
    private String categoryKey;

    @Column
    private Boolean visible = true;

    @ManyToMany(mappedBy = "categorySet")
    private Set<ArticleEntity> articleSet;

    @Column
    private LocalDateTime createdDate;

}
