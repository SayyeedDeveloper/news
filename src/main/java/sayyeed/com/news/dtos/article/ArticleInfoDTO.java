package sayyeed.com.news.dtos.article;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import sayyeed.com.news.dtos.AttachDTO;
import sayyeed.com.news.enums.article.ArticleStatusEnum;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleInfoDTO {
    private Integer id;
    private String title;
    private String description;
    private String content;
    private String imageId;
    private AttachDTO image;
    private Integer regionId;
    private ArticleStatusEnum status;
    private Integer readTime;
    private LocalDateTime createdDate;
    private LocalDateTime publishedDate;
    private List<Integer> categories;
    private List<Integer> sections;
}
