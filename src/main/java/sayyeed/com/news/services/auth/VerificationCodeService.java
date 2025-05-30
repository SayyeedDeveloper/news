package sayyeed.com.news.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sayyeed.com.news.entities.VerificationCodeEntity;
import sayyeed.com.news.repositories.VerificationCodeRepository;

import java.nio.file.LinkOption;
import java.time.LocalDateTime;

@Service
public class VerificationCodeService {

    @Autowired
    private VerificationCodeRepository repository;

    public String generateCode(String username) {

        // delete old codes by username
        repository.deleteByUsername(username);

        // generate random 5 code
        String code = String.format("%05d", new java.util.Random().nextInt(10000, 100000));

        VerificationCodeEntity entity = new VerificationCodeEntity();
        entity.setCode(code);
        entity.setUsername(username);
        entity.setSentTime(LocalDateTime.now());

        // save
        repository.save(entity);

        return code;
    }
}
