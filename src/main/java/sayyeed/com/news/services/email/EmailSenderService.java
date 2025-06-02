package sayyeed.com.news.services.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import sayyeed.com.news.utils.CodeGenerator;

@Service
public class EmailSenderService {
    @Value("${spring.mail.username}")
    private String fromAccount;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private EmailHistoryService emailHistoryService;

    /*   Send Verification Code using Email
    public void sendVerificationCode(String toAccount){
        String code = CodeGenerator.fiveDigit();
        String subject = "Verification Code !";
        String body = "Do Not Give this Code to Anyone, code: " + code +" ";
        sendSimpleMessage(subject, body, toAccount);
        emailHistoryService.save(body, code, toAccount);
    }
     */

    public void sendVerificationLink(String toAccount) {
        String code = CodeGenerator.fiveDigit();
        String subject = "Verification Link !";
        String body = "Click here to Verification: http://localhost:8080/api/auth/registration/email/verification/%s/%s";
        body = String.format(body, toAccount, code);

        //send
        sendSimpleMessage(subject, body, toAccount);

        //save to db
        emailHistoryService.save(body, code, toAccount);
    }

    private void sendSimpleMessage(String subject, String body, String toAccount) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(fromAccount);
        msg.setTo(toAccount);
        msg.setSubject(subject);
        msg.setText(body);
        javaMailSender.send(msg);
    }

}
