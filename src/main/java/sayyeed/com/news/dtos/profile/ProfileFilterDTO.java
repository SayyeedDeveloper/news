package sayyeed.com.news.dtos.profile;

import lombok.Getter;
import lombok.Setter;
import sayyeed.com.news.enums.profile.ProfileRoleEnum;
import sayyeed.com.news.enums.profile.ProfileStatusEnum;

import java.time.LocalDate;

@Getter
@Setter
public class ProfileFilterDTO {
    private String query;
    private ProfileStatusEnum status;
    private ProfileRoleEnum role;
    private LocalDate createdDateFrom;
    private LocalDate createdDateTo;
}
