package sayyeed.com.news.entities.article;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sayyeed.com.news.entities.CategoryEntity;
import sayyeed.com.news.entities.RegionEntity;
import sayyeed.com.news.entities.SectionEntity;
import sayyeed.com.news.enums.article.ArticleStatusEnum;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "article")
@Getter
@Setter
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private String content;

    @Column
    private Integer SharedCount;

    @Column
    private String imageId;

    @Column(name = "region_id")
    private Integer regionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", insertable = false, updatable = false)
    private RegionEntity region;

    @Column
    private Integer moderatorId;

    @Column
    private Integer publisherId;

    @Column
    private ArticleStatusEnum status;

    @Column
    private Integer readTime;

    @Column
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime publishedDate;

    @Column
    private Boolean visible;

    @Column
    private Integer viewCount;

    @ManyToMany
    @JoinTable(name = "article_category",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<CategoryEntity> categorySet;

    @ManyToMany
    @JoinTable(name = "article_section",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "section_id"))
    private Set<SectionEntity> sectionSet;
}
