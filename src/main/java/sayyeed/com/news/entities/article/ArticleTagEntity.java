package sayyeed.com.news.entities.article;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sayyeed.com.news.entities.TagEntity;

@Entity
@Table(name = "article_tags")
@Setter
@Getter
public class ArticleTagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "tag_name")
    private String tagName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_name", insertable = false, updatable = false)
    private TagEntity tag;

    @Column(name = "article_id")
    private String articleId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artilce_id", insertable = false, updatable = false)
    private ArticleEntity article;
}
