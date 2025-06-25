package sayyeed.com.news.mapper;

import java.time.LocalDateTime;

public interface ArticleShortInfo {
    Integer getId();
    String getTitle();
    String getDescription();
    String getImageId();
    LocalDateTime getPublishedDate();
}
