package sayyeed.com.news.dtos.profile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import sayyeed.com.news.enums.profile.ProfileStatusEnum;
import sayyeed.com.news.enums.profile.ProfileRoleEnum;

import java.util.List;

@Getter
@Setter
public class ProfileUpdateDTO {

    @NotBlank(message = "Name required")
    private String name;

    @NotBlank(message = "Surname required")
    private String surname;

    @NotBlank(message = "Username required")
    private String username;

    private ProfileStatusEnum status;

    private String photoId;

    @NotEmpty(message = "Profile at least have one role")
    private List<ProfileRoleEnum> roles;
}
