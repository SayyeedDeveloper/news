package sayyeed.com.news.dtos.article;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ArticleUpdateDTO {

    @NotBlank(message = "Title required")
    private String title;

    @NotBlank(message = "Description required")
    private String description;

    @NotBlank(message = "Content required")
    private String content;

    @NotBlank(message = "ImageId required")
    private String imageId;

    private Integer regionId;

    @NotEmpty(message = "Article must contain at least one categories")
    private List<Integer> categories;

    @NotEmpty(message = "Article must contain at least one section")
    private List<Integer> sections;

}
