package sayyeed.com.news.dtos.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationDTO { //todo validation
    private String name;
    private String surname;
    private String username;
    private String password;
}
