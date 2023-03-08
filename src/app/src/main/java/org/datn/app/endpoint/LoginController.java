package org.datn.app.endpoint;

import lombok.RequiredArgsConstructor;
import org.datn.app.core.entity.User;
import org.datn.app.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LoginController {

    private final UserService userService;

    private final OAuth2AuthorizedClientService authorizedClientService;

    @PostMapping("/google/login")
    public ResponseEntity<?> loginWithGoogle(@RequestBody OAuth2AuthenticationToken oauth2Token) {
        OAuth2User user = oauth2Token.getPrincipal();
        String email = user.getAttribute("email");
        if (userService.findByEmail(email) == null) {
            User user1 = new User();
            user1.setEmail(email);
            user1.setFullName((user.getAttribute("given_name")) == null ? "" : user.getAttribute("given_name"));
            user1.setCreated(new Date());
            user1.setAddress(user.getAttribute("address") == null ? "" : user.getAttribute("address"));
            user1.setRole("ROLE_USER");
            user1.setUsername(email);
            userService.doInsert(user1);
        }
        OAuth2AccessToken accessToken = oauth2Token.getAuthorizedClientRegistrationId().equals("google") ?
                authorizedClientService.loadAuthorizedClient("google", oauth2Token.getName()).getAccessToken() :
                authorizedClientService.loadAuthorizedClient("facebook", oauth2Token.getName()).getAccessToken();

        return ResponseEntity.ok(accessToken);

    }
    @PostMapping("/google/logout")
    public ResponseEntity<?> logoutWithGoogle(@RequestBody Map<String, Object> body) {
        OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) body.get("oauth2Token");
        authorizedClientService.removeAuthorizedClient(oauth2Token.getAuthorizedClientRegistrationId(), oauth2Token.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
