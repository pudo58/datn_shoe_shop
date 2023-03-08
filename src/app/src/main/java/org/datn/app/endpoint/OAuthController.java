package org.datn.app.endpoint;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;


@RestController
@RequiredArgsConstructor
public class OAuthController {

    @GetMapping("/api/auth/code")
    public String getGoogleOAuth2Code(HttpServletRequest request) {
        String code = request.getParameter("code");
        return code;
    }
    @GetMapping("/api/auth/check")
    public String checkGoogleOAuth2Code(Principal principal, @RequestParam String code) {
        System.out.println(principal);
        return code;
    }

}
