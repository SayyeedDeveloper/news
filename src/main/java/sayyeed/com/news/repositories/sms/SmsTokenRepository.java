package sayyeed.com.news.repositories.sms;

import org.springframework.data.repository.CrudRepository;
import sayyeed.com.news.entities.sms.SmsTokenEntity;

import java.util.Optional;

public interface SmsTokenRepository extends CrudRepository<SmsTokenEntity, Integer> {
    Optional<SmsTokenEntity> findTopByOrderByLocalDateTimeDesc();
}
