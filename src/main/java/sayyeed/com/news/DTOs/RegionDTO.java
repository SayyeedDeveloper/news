package sayyeed.com.news.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegionDTO {
    private Integer id;
    private Integer orderNumber;
    private String nameUz;
    private String nameRu;
    private String nameEn;
}
