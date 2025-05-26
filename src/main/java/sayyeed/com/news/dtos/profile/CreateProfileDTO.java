package sayyeed.com.news.dtos.profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import sayyeed.com.news.enums.ProfileStatusEnum;
import sayyeed.com.news.enums.UserRoleEnum;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateProfileDTO {
    private Integer id;

    @NotBlank(message = "Name required")
    private String name;

    @NotBlank(message = "Surname required")
    private String surname;

    @NotBlank(message = "Username required")
    private String username;

    @NotBlank(message = "Password required")
    private String password;

    private ProfileStatusEnum status;

    private LocalDateTime createdDate;

    private String photoId;

    @NotEmpty(message = "Profile at least have one role")
    private List<UserRoleEnum> roles;
}
