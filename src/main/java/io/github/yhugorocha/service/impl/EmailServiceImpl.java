package io.github.yhugorocha.service.impl;

import io.github.yhugorocha.domain.entity.Email;
import io.github.yhugorocha.domain.enums.StatusEmail;
import io.github.yhugorocha.domain.repositorio.Emails;
import io.github.yhugorocha.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailServiceImpl {

    @Autowired
    Emails emails;

    @Autowired
    private JavaMailSender emailSender;

    public Email sendEmail(Email email) {

        email.setSend_date_email(LocalDateTime.now());

        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(email.getEmail_from());
            message.setTo(email.getEmail_to());
            message.setSubject(email.getSubject());
            message.setText(email.getTexto());
            emailSender.send(message);

            email.setStatus_email(StatusEmail.SENT);
        }catch (MailException e){
            email.setStatus_email(StatusEmail.ERROR);
        }finally {
            return null;
        }
    }
}
