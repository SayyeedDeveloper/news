package sayyeed.com.news.services.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import sayyeed.com.news.utils.CodeGenerator;
import sayyeed.com.news.utils.JwtUtil;

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

//    public void sendVerificationLink(String toAccount) {
//        String code = CodeGenerator.fiveDigit();
//        String encodedToken = JwtUtil.encode(toAccount, code);
//        String subject = "Verification Link !";
//        String body = "Click here to Verification: http://localhost:8080/api/auth/registration/email/verification/%s";
//        body = String.format(body, encodedToken);
//
//        //send
//        sendSimpleMessage(subject, body, toAccount);
//
//        //save to db
//        emailHistoryService.save(body, code, toAccount);
//    }

    public void sendVerificationHtml(String toAccount) {
        String code = CodeGenerator.fiveDigit();
        String encodedToken = JwtUtil.encode(toAccount, code);
        String subject = "Verification Link !";
        String body = """
                <!DOCTYPE html>
                <html lang="en">
                  <head>
                    <meta charset="UTF-8" />
                    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
                    <title>Verification</title>
                  </head>
                  <body style="background-color: #f3f3f3; margin: 0; padding: 20px; text-align: center;">
                    <table cellpadding="0" cellspacing="0" border="0" width="100%%" style="max-width: 600px; margin: 0 auto; background-color: #fff; border-radius: 5px; box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);">
                      <tr>
                        <td align="center" style="padding: 20px;">
                          <h1>This is Verification Email</h1>
                          <h4>You have 30 minutes to verify your email</h4>
                          <a\s
                             href="http://localhost:8080/api/auth/registration/email/verification/%s"
                            style="display: inline-block; background-color: #5bb1fa; color: white; padding: 10px 20px; border: none; border-radius: 4px; cursor: pointer; font-size: 16px; text-decoration: none; margin: 10px 0;"
                          >
                            Tap to confirm
                          </a>
                          <p style="color: #808080">If you get verified come back to website</p>
                        </td>
                      </tr>
                    </table>
                  </body>
                </html>""";
        body = String.format(body, encodedToken);

        //send
        sendMimeMessage(subject, body, toAccount);

        body = "Verification code !";

        //save to db
        emailHistoryService.save(body, code, toAccount);
    }

//    private void sendSimpleMessage(String subject, String body, String toAccount) {
//        SimpleMailMessage msg = new SimpleMailMessage();
//        msg.setFrom(fromAccount);
//        msg.setTo(toAccount);
//        msg.setSubject(subject);
//        msg.setText(body);
//        javaMailSender.send(msg);
//    }

    private String sendMimeMessage(String subject, String body, String toAccount) {
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            msg.setFrom(fromAccount);

            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setTo(toAccount);
            helper.setSubject(subject);
            helper.setText(body, true);
            javaMailSender.send(msg);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return "Mail was send";
    }


}
