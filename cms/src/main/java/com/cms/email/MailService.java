package com.cms.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Autowired
    MailConfiguration mailConfiguration;

    @Async
    public <T extends MessageProperties> void sendMail(String from, String to, T messageProperties)
            throws MessagingException {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailConfiguration.getHost());
        mailSender.setPort(mailConfiguration.getPort());
        mailSender.setUsername(mailConfiguration.getUsername());
        mailSender.setPassword(mailConfiguration.getPassword());

        // SimpleMailMessage mailMessage = new SimpleMailMessage();

        // mailMessage.setFrom(from);
        // mailMessage.setTo(to);
        // mailMessage.setSubject(messageProperties.getSubject());
        // mailMessage.setText(messageProperties.buildMessage());

        // mailSender.send(mailMessage);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setText(messageProperties.buildMessage(), true); 
        helper.setTo(to);
        helper.setSubject(messageProperties.getSubject());
        helper.setFrom(from);
        mailSender.send(mimeMessage);

    }

}
