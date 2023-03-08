package org.datn.app.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.datn.app.core.entity.User;
import org.datn.app.core.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2LoginGoogle extends SimpleUrlAuthenticationSuccessHandler {
    private final UserService userService;
    private final OAuth2AuthorizedClientService authorizedClientService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) authentication;
        OAuth2User user = oauth2Token.getPrincipal();
        String email = user.getAttribute("email");

        if (userService.findByEmail(email) == null) {
            User user1 = new User();
            user1.setEmail(email);
            user1.setFullName((user.getAttribute("given_name")) == null ? "" : user.getAttribute("given_name"));
            user1.setCreated(new Date());
            user1.setAddress(user.getAttribute("address") == null ? "" : user.getAttribute("address"));
            user1.setRole("ROLE_USER");
            userService.doInsert(user1);
            OAuth2AccessToken accessToken = oauth2Token.getAuthorizedClientRegistrationId().equals("google") ?
                    authorizedClientService.loadAuthorizedClient("google", oauth2Token.getName()).getAccessToken() :
                    authorizedClientService.loadAuthorizedClient("facebook", oauth2Token.getName()).getAccessToken();
            log.info("Access token: {}", accessToken.getTokenValue());
            user1.setAccessToken(accessToken.getTokenValue());
            userService.doUpdateById(user1,user1.getId());
            log.info("New user {} created by method Google: ", user1.getFullName());
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }

}

