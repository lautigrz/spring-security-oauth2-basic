package com.oauth.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/hello")
    public String home() {
        return "Welcome to the Home Page!";
    }

    @GetMapping("/helloSecured")
    public String secured(@AuthenticationPrincipal OAuth2User principal) {
        // Todos los atributos que devuelve GitHub
        Map<String, Object> attributes = principal.getAttributes();

        // El username de GitHub está en el atributo "login"
        String githubUsername = principal.getAttribute("login");

        return "Hola " + githubUsername + ", te logueaste con GitHub!";
    }

}
