package sayyeed.com.news.dtos.article;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ArticleLastPublishedDTO {
    private List<ArticleInfoDTO> excludeIds;
    private int limit;
}
