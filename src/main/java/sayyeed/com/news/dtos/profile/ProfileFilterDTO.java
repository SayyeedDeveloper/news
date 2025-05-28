package sayyeed.com.news.dtos.profile;

import lombok.Data;
import sayyeed.com.news.enums.profile.ProfileRoleEnum;
import sayyeed.com.news.enums.profile.ProfileStatusEnum;

import java.time.LocalDate;

@Data
public class ProfileFilterDTO {
    private String query;
    private ProfileStatusEnum status;
    private ProfileRoleEnum role;
    private LocalDate createdDateFrom;
    private LocalDate createdDateTo;
}
