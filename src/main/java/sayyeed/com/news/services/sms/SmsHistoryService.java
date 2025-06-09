package sayyeed.com.news.services.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sayyeed.com.news.entities.sms.SmsHistoryEntity;
import sayyeed.com.news.exceptions.AppBadException;
import sayyeed.com.news.repositories.sms.SmsHistoryRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SmsHistoryService {
    @Autowired
    private SmsHistoryRepository repository;

    public void save(String phone, String body, String code) {
        SmsHistoryEntity entity = new SmsHistoryEntity();
        entity.setPhoneNumber(phone);
        entity.setBody(body);
        entity.setCode(code);
        entity.setDateTime(LocalDateTime.now());
        repository.save(entity);
    }

    public SmsHistoryEntity getSmsByPhone(String phoneNumber) {
        Optional<SmsHistoryEntity> optional = repository.findTopByPhoneNumberOrderByDateTimeDesc(phoneNumber);
        if (optional.isEmpty()) {
            throw new AppBadException("Invalid phone number");
        }
        return optional.get();
    }

    public void increaseAttempt(String id) {
        repository.increaseAttempt(id);
    }
}
