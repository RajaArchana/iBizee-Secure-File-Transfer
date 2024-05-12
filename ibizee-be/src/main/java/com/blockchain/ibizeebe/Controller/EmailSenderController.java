package com.blockchain.ibizeebe.Controller;

import com.blockchain.ibizeebe.Model.EmailRequest;
import com.blockchain.ibizeebe.Model.GeneralResponse;
import com.blockchain.ibizeebe.Service.EmailSenderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailSenderController {

    private final EmailSenderService emailSenderService;

    public EmailSenderController(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @PostMapping("/email/send")
    public GeneralResponse sendEmail(@RequestBody EmailRequest emailRequest) {
        return emailSenderService.sendEmail(emailRequest);
    }

}
