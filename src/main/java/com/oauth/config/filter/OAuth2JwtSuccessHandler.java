package com.oauth.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oauth.config.jwt.JwtUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@Slf4j
public class OAuth2JwtSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String username = oauth2User.getAttribute("login"); // Asumiendo que el atributo "login" contiene el nombre de usuario de GitHub
        String token = jwtUtils.generateToken(username);
        response.addHeader("Authorization", token);

        Map<String, Object> httpResponse = Map.of(
                "token", token,
                "user", username,
                "message", "Authentication successful"
        );

        log.info("Authentication successful");

        response.getWriter().write(new ObjectMapper().writeValueAsString(httpResponse));
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().flush();

    }
}
