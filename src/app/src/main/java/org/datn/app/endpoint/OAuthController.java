package org.datn.app.endpoint;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class OAuthController {
    private final OAuth2AuthorizedClientService authorizedClientService;
    private final GoogleAuthorizationCodeFlow flow;

    @GetMapping("/api/auth/code")
    public String getGoogleOAuth2Code(Principal principal) throws IOException {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) principal;
        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(
                oauthToken.getAuthorizedClientRegistrationId(), oauthToken.getName());
        if (authorizedClient != null) {
            return authorizedClient.getAccessToken().getTokenValue();
        }else{
            AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl().setRedirectUri("http://localhost:8080/api/auth/code");
            return authorizationUrl.build();
        }
    }
}
