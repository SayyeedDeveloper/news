package sayyeed.com.news.entities.article;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sayyeed.com.news.entities.AttachEntity;
import sayyeed.com.news.entities.RegionEntity;
import sayyeed.com.news.entities.profile.ProfileEntity;
import sayyeed.com.news.enums.article.ArticleStatusEnum;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "article")
@Getter
@Setter
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title", columnDefinition = "text")
    private String title;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "content", columnDefinition = "text")
    private String content;

    @Column
    private Integer sharedCount;

    @Column(name = "image_id")
    private String imageId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id", insertable = false, updatable = false)
    private AttachEntity image;

    @Column(name = "region_id")
    private Integer regionId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", insertable = false, updatable = false)
    private RegionEntity region;

    @Column(name = "moderator_id")
    private Integer moderatorId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moderator_id", insertable = false, updatable = false)
    private ProfileEntity moderator;

    @Column(name = "publisher_id")
    private Integer publisherId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id", updatable = false, insertable = false)
    private ProfileEntity publisher;

    @Enumerated(EnumType.STRING)
    @Column
    private ArticleStatusEnum status;

    @Column
    private Integer readTime;

    @Column
    private Integer viewCount;

    @Column
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime publishedDate;

    @Column
    private Boolean visible = Boolean.TRUE;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticleCategoryEntity> articleCategories = new ArrayList<>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticleSectionEntity> articleSections = new ArrayList<>();


    //todo: update article entity
    //todo update article request / response
}
