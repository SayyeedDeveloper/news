package sayyeed.com.news.dtos.article;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ArticleFilterDTO {
    // title,region_id,category_id,published_date_from,published_date_to
    private String title;
    private Integer regionId;
    private Integer categoryId;
    private LocalDate publishedDateFrom;
    private LocalDate publishedDateTo;
}
