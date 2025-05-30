package sayyeed.com.news.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.SpringApplicationEvent;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import sayyeed.com.news.services.auth.VerificationCodeService;

@Service
public class EmailSenderService {
    @Value("${spring.mail.username}")
    private String fromAccount;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private VerificationCodeService verificationCodeService;

    public void sendVerificationCode(String toAccount){
        String code = verificationCodeService.generateCode(toAccount);
        String subject = "Verification Code !";
        String body = "Do Not Give this Code to Anyone, code: " + code +" ";
        sendSimpleMessage(subject, body, toAccount);
    }

    public void sendVerificationLink(String toAccount) {
        String code = verificationCodeService.generateCode(toAccount);
        String subject = "Verification Code !";
        String body = "Do Not Give this Code to Anyone, code: " + code +" ";
        sendSimpleMessage(subject, body, toAccount);
    }

    private String sendSimpleMessage(String subject, String body, String toAccount) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(fromAccount);
        msg.setTo(toAccount);
        msg.setSubject(subject);
        msg.setText(body);
        javaMailSender.send(msg);

        return "Mail was send";
    }

}
