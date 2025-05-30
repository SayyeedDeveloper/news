package sayyeed.com.news.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import sayyeed.com.news.entities.VerificationCodeEntity;

public interface VerificationCodeRepository extends CrudRepository<VerificationCodeEntity, Integer> {

    @Transactional
    @Modifying
    @Query("delete from VerificationCodeEntity where username =?1")
    void deleteByUsername(String username);

}
