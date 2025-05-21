package sayyeed.com.news.DTOs;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RegionDTO {
    private Integer id;

    @NotNull(message = "orderNumber required")
    @Min(value = 1, message = "orderNumber have to higher than 0")
    private Integer orderNumber;

    @NotBlank(message = "nameUz required")
    private String nameUz;

    @NotBlank(message = "nameRu required")
    private String nameRu;

    @NotBlank(message = "nameEn required")
    private String nameEn;

    @NotNull(message = "regionKey required")
    private Integer regionKey;

    private LocalDateTime createdDate;
}
