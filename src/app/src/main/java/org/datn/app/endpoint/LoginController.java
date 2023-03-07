package org.datn.app.endpoint;

import org.datn.app.core.entity.User;
import org.datn.app.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @PostMapping("/google/login")
    public ResponseEntity<?> loginWithGoogle(@RequestBody User user) {
        // OAuth2 login google
        if (userService.findByEmail(user.getEmail()) == null) {
            user.setCreated(new Date());
            user.setRole("ROLE_USER");
            user.setUsername(user.getEmail());
            userService.doInsert(user);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/google/logout")
    public ResponseEntity<?> logoutWithGoogle(@RequestBody Map<String, Object> body) {
        OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) body.get("oauth2Token");
        authorizedClientService.removeAuthorizedClient(oauth2Token.getAuthorizedClientRegistrationId(), oauth2Token.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
