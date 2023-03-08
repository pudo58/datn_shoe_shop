package org.datn.app.config;

import lombok.RequiredArgsConstructor;
import org.datn.app.filter.OAuth2LoginGoogle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final OAuth2LoginGoogle loginOAuth2Process;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        http
                .authorizeRequests()
                .antMatchers("/api/**","/api/auth/**", "/webjars/**", "/swagger-ui.html", "/swagger-ui/**", "/swagger-resources/**", "/v2/api-docs","/v3/api-docs/**","/logout")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .oauth2Login()
                .successHandler(loginOAuth2Process)
                .and().logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .logoutUrl("/api/logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .clearAuthentication(true)
                );
    }
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(Collections.singletonList("*"));
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new org.springframework.web.filter.CorsFilter(source);
    }
}
