package sayyeed.com.news.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sayyeed.com.news.enums.profile.ProfileRoleEnum;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtDTO {

    public JwtDTO(String username, String code) {
        this.username = username;
        this.code = code;
    }

    private String username;
    private String code;
    private List<ProfileRoleEnum> roles;
}
