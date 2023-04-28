package org.datn.app.config;

import lombok.RequiredArgsConstructor;
import org.datn.app.util.MailSenderUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.mail.MessagingException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class WebConfig {
    private final JavaMailSender mailSender;
    private final MailSenderUtil mailSenderUtil;
    @Bean
    public LocalValidatorFactoryBean validatorFactoryBean() {
        return new LocalValidatorFactoryBean();
    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(10000000);
        return multipartResolver;
    }

    @Async
    public void sendMail(String email, String code,String subject) throws MessagingException {
        String content = mailSenderUtil.sendMail(subject,code);
        MimeMessageHelper helper = new MimeMessageHelper(mailSender.createMimeMessage(), StandardCharsets.UTF_8.name());
        helper.setCc(email);
        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(helper.getMimeMessage());
    }
}
