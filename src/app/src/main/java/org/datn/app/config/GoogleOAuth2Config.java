package org.datn.app.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.FileReader;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Configuration
@RequiredArgsConstructor
public class GoogleOAuth2Config {
    @Bean
    public GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow() throws IOException, GeneralSecurityException {
        // Khởi tạo GoogleAuthorizationCodeFlow ở đây
        GoogleClientSecrets secrets = GoogleClientSecrets.load(
                JacksonFactory.getDefaultInstance(), new FileReader("client_secret.json"));
        return new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                secrets,
                Collections.singleton("https://www.googleapis.com/auth/calendar"))
                .build();
    }
}
