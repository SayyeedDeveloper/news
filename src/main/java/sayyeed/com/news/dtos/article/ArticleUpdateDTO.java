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
public class ArticleUpdateDTO {

    @NotBlank(message = "Title required")
    private String title;

    @NotBlank(message = "Description required")
    private String description;

    @NotBlank(message = "Content required")
    private String content;

    @NotBlank(message = "ImageId required")
    private String imageId;

    @NotNull(message = "Read time required")
    private Integer readTime;

    private Integer regionId;

    @NotEmpty(message = "Article must contain at least one categories")
    private List<CategoryDTO> categories;

    @NotEmpty(message = "Article must contain at least one section")
    private List<SectionDTO> sections;

}
