package sayyeed.com.news.dtos.sms;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmsTokenProviderResponse {
    private String message;
    private Data data;
    private String token_type;

    @Getter
    @Setter
    public static class Data {
        private String token;
    }
}
