package sayyeed.com.news.dtos.article;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ArticleShortInfoDTO {
    private Integer id;
    private String title;
    private String description;
    private String imageId;
    private LocalDateTime publishedDate;
}
