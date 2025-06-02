package sayyeed.com.news.services.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sayyeed.com.news.entities.EmailHistoryEntity;
import sayyeed.com.news.enums.profile.ProfileStatusEnum;
import sayyeed.com.news.repositories.EmailHistoryRepository;
import sayyeed.com.news.services.profile.ProfileService;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class EmailHistoryService {

    @Autowired
    private EmailHistoryRepository repository;

    @Autowired
    private ProfileService profileService;

    public void save(String body, String code, String toUsername) {
        EmailHistoryEntity entity = new EmailHistoryEntity();

        // set values
        entity.setBody(body);
        entity.setCode(code);
        entity.setUsername(toUsername);
        entity.setSentTime(LocalDateTime.now());

        // save
        repository.save(entity);
    }

    public Boolean isEmailSend(String username, String code) {
        Optional<EmailHistoryEntity> optional = repository.findTopByUsernameOrderBySentTimeDesc(username);
        if (optional.isEmpty()) {
            return false;
        }
        EmailHistoryEntity entity = optional.get();

        // expired time
        LocalDateTime expiredTime = entity.getSentTime().plusMinutes(2);
        if (LocalDateTime.now().isAfter(expiredTime)) {
            return false;
        }

        // check attempt count
        Integer attempts = entity.getAttemptCount();

        // increase attempt count
        repository.incrementAttemptCountById(entity.getId());

        if (attempts >= 5) {
            return false;
        }
        if (code.equals(entity.getCode())) {
            profileService.setStatusByUsername(ProfileStatusEnum.ACTIVE, username);
            return true;
        }
        return false;
    }
}
