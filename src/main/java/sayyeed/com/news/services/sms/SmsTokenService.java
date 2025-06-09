package sayyeed.com.news.services.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import sayyeed.com.news.dtos.sms.SmsProviderTokenDTO;
import sayyeed.com.news.dtos.sms.SmsTokenProviderResponse;
import sayyeed.com.news.entities.sms.SmsTokenEntity;
import sayyeed.com.news.exceptions.AppBadException;
import sayyeed.com.news.repositories.sms.SmsTokenRepository;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SmsTokenService {

    @Autowired
    private SmsTokenRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    public String getToken() {
        Optional<SmsTokenEntity> optional = repository.findTopByOrderByLocalDateTimeDesc();
        if (optional.isPresent()) {
            SmsTokenEntity smsTokenEntity = optional.get();
            LocalDateTime tokenDate = smsTokenEntity.getLocalDateTime();
            LocalDateTime now = LocalDateTime.now();
            long days = Duration.between(tokenDate, now).toDaysPart();

            if (days >= 30) {
                // create new and return token
                return createToken();
            } else if (days == 29) {
                // refresh token
                return refreshToken(smsTokenEntity.getToken());
            }else {
                return smsTokenEntity.getToken();
            }
        }
        createToken();
        throw new AppBadException("Sms service isn't working");
    }

    private String createToken() {
        SmsProviderTokenDTO smsProviderTokenDTO = new SmsProviderTokenDTO();
        smsProviderTokenDTO.setEmail("tmuhammadsayyid11@gmail.com");
        smsProviderTokenDTO.setPassword("UnRbb63AFu8sC7VN7Ri76dBrSueKhAcEFaof1HOo");

        String url = "https://notify.eskiz.uz/api/auth/login";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        RequestEntity<SmsProviderTokenDTO> request = RequestEntity
                .post(url)
                .headers(headers)
                .body(smsProviderTokenDTO);

        var response = restTemplate.exchange(request, SmsTokenProviderResponse.class);
        String token = response.getBody().getData().getToken();

        SmsTokenEntity entity = new SmsTokenEntity();
        entity.setToken(token);
        entity.setLocalDateTime(LocalDateTime.now());
        repository.save(entity);

        return token;
    }

    public String refreshToken(String oldToken) {
        String url = "https://notify.eskiz.uz/api/auth/refresh";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + oldToken);
        headers.set("Content-Type", "application/json");

        RequestEntity<Void> request = RequestEntity
                .patch(url)
                .headers(headers)
                .build();

        var response = restTemplate.exchange(request, SmsTokenProviderResponse.class);
        String newToken = response.getBody().getData().getToken();

        SmsTokenEntity entity = new SmsTokenEntity();
        entity.setToken(newToken);
        entity.setLocalDateTime(LocalDateTime.now());
        repository.save(entity);

        return newToken;
    }

}
