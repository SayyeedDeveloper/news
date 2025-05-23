package sayyeed.com.news.dtos.profile;

import lombok.Getter;
import lombok.Setter;
import sayyeed.com.news.enums.ProfileStatusEnum;
import sayyeed.com.news.enums.UserRoleEnum;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ProfileInfoDTO {
    private Integer id;
    private String name;
    private String surname;
    private String username;
    private ProfileStatusEnum status;
    private Boolean visible;
    private LocalDateTime createdDate;
    private String photoId;
    private List<UserRoleEnum> roles;
}