package com.blockchain.ibizeebe.Service;

import com.blockchain.ibizeebe.Model.EmailRequest;
import com.blockchain.ibizeebe.Model.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    private final JavaMailSender javaMailSender;

    public EmailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /**
     * function send emails to the user
     */
    public GeneralResponse sendEmail(EmailRequest emailRequest) {
        System.out.println("***** sendEmail function is starting *****");

        GeneralResponse generalResponse = new GeneralResponse();

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("ibizeefiletransfer@gmail.com");
            message.setTo(emailRequest.getTo());
            message.setSubject(emailRequest.getSubject());
            message.setText(emailRequest.getBody());
            javaMailSender.send(message);
            System.out.println("Email sent successfully");

            generalResponse.setResponseCode(200);
            generalResponse.setResponseMessage("Email sent successfully");
        } catch (Exception e) {
            System.out.println("Email sending failed!");
            generalResponse.setResponseCode(400);
            generalResponse.setResponseMessage("Email sending failed!");
            e.printStackTrace();
        }
        return generalResponse;
    }
}
