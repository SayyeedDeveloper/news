package sayyeed.com.news.dtos.article;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import sayyeed.com.news.dtos.CategoryDTO;
import sayyeed.com.news.dtos.SectionDTO;

import java.util.List;

@Getter
@Setter
public class ArticleCreateDTO {

    @NotBlank(message = "Title required")
    private String title;

    @NotBlank(message = "Description required")
    private String description;

    @NotBlank(message = "Content required")
    private String content;

    @NotBlank(message = "ImageId required")
    private String imageId;

    private Integer regionId;

    @NotNull(message = "Read time required! in seconds")
    private Integer readTime;

    @NotEmpty(message = "Article must contain at least one categories")
    private List<CategoryDTO> categories;

    @NotEmpty(message = "Article must contain at least one section")
    private List<SectionDTO> sections;

}
