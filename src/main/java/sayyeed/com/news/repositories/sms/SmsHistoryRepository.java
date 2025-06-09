package sayyeed.com.news.repositories.sms;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import sayyeed.com.news.entities.sms.SmsHistoryEntity;

import java.util.Optional;

public interface SmsHistoryRepository extends CrudRepository<SmsHistoryEntity, String> {
    Optional<SmsHistoryEntity> findTopByPhoneNumberOrderByDateTimeDesc(String phoneNumber);

    @Transactional
    @Modifying
    @Query("update SmsHistoryEntity set attemptCount = attemptCount + 1 where id = ?1")
    void increaseAttempt(String id);
}
