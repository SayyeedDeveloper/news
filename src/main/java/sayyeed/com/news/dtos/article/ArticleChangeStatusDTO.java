package sayyeed.com.news.dtos.article;

import lombok.Getter;
import lombok.Setter;
import sayyeed.com.news.enums.article.ArticleStatusEnum;

@Getter
@Setter
public class ArticleChangeStatusDTO {
    private Integer id;
    private ArticleStatusEnum status;
}
