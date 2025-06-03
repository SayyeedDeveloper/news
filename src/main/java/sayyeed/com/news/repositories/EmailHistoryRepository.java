package sayyeed.com.news.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import sayyeed.com.news.entities.EmailHistoryEntity;

import java.util.Optional;

public interface EmailHistoryRepository extends CrudRepository<EmailHistoryEntity, Integer> {

    @Transactional
    @Modifying
    @Query("update EmailHistoryEntity  set attemptCount = attemptCount + 1 where id=?1")
    void incrementAttemptCountById(Long id);

    Optional<EmailHistoryEntity> findTopByUsernameOrderBySentTimeDesc(String username);

}
