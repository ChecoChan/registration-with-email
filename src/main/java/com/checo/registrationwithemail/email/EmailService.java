package com.checo.registrationwithemail.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@Service
public class EmailService implements EmailSender {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    @Async
    public void send(String to, String email) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
            messageHelper.setFrom("welcome@checo.com");
            messageHelper.setTo(to);
            messageHelper.setSubject("Confirm your email");
            messageHelper.setText(email, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("failed to send email");
            throw new IllegalStateException("failed to send email");
        }
    }
}
