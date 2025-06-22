package sayyeed.com.news.services.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sayyeed.com.news.entities.EmailHistoryEntity;
import sayyeed.com.news.exceptions.AppBadException;
import sayyeed.com.news.repositories.EmailHistoryRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class EmailHistoryService {

    @Autowired
    private EmailHistoryRepository repository;


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

    public EmailHistoryEntity isEmailSent(String username) {
        Optional<EmailHistoryEntity> optional = repository.findTopByUsernameOrderBySentTimeDesc(username);
        if (optional.isEmpty()) {
            throw new AppBadException("User doesn't exist");
        }
        return optional.get();
    }

    public void incrementAttemptCountById(Long id) {
        repository.incrementAttemptCountById(id);
    }
}
