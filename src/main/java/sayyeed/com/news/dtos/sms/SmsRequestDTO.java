package sayyeed.com.news.dtos.sms;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmsRequestDTO {
    private String mobile_number;
    private String message;
}
