package sayyeed.com.news.dtos.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerificationBySmsDTO {
    @NotBlank(message = "Phone number required")
    private String phoneNumber;
    @NotBlank(message = "Code required")
    private String code;
}
