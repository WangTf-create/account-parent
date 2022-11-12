package com.citi.mvnbook.account.email.service.impl;

import com.citi.mvnbook.account.email.service.AccountEmailService;
import com.citi.mvnbook.account.email.exceptions.AccountEmailException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author wangtongfa
 * @date 2022/10/18 22:17
 */
@Service
public class AccountEmailServiceImpl implements AccountEmailService {


    @Resource
    private JavaMailSender mailSender;

    @Getter
    @Setter
    @Value("${spring.mail.username}")
    private String systemMail;

    @Override
    public void sendMail(String to, String subject, String htmlText) throws AccountEmailException {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message);


            messageHelper.setFrom(systemMail);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(htmlText,true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new AccountEmailException("Failed to send email", e);
        }

    }
}
