package sayyeed.com.news.services.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sayyeed.com.news.dtos.sms.SmsRequestDTO;
import sayyeed.com.news.utils.CodeGenerator;

@Service
public class SmsSenderService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SmsTokenService smsTokenService;

    @Autowired
    private SmsHistoryService smsHistoryService;

    public void sendRegistrationSMS(String phone) {
        String code = CodeGenerator.fiveDigit();
        String body = "Bu Eskiz dan test";
        try {
            sendSms(phone, body);
            smsHistoryService.save(phone, body, code);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void sendSms(String phone, String body) {
        SmsRequestDTO smsRequestDTO = new SmsRequestDTO();
        smsRequestDTO.setMobile_phone(phone);
        smsRequestDTO.setMessage(body);

        String url = "https://notify.eskiz.uz/api/message/sms/send";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + smsTokenService.getToken());

        RequestEntity<SmsRequestDTO> request = RequestEntity
                .post(url)
                .headers(headers)
                .body(smsRequestDTO);

        restTemplate.exchange(request, String.class);

    }
}
