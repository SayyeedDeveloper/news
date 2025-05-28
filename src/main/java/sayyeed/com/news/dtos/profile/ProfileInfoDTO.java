package sayyeed.com.news.dtos.profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import sayyeed.com.news.enums.ProfileStatusEnum;
import sayyeed.com.news.enums.ProfileRoleEnum;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileInfoDTO {
    private Integer id;
    private String name;
    private String surname;
    private String username;
    private ProfileStatusEnum status;
    private LocalDateTime createdDate;
    private String photoId;
    private List<ProfileRoleEnum> roles;
}