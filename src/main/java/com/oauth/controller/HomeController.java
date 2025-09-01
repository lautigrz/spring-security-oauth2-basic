package com.oauth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/hello")
    public String home() {
        return "Welcome to the Home Page!";
    }

    @GetMapping("/helloSecured")
    public String homeSecured() {
        return "Welcome to the Secured Home Page!";
    }

}
