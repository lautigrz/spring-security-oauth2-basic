package com.oauth.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String accessAdmin() {
        return "Hello Admin Secured";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public String accessUser() {
        return "Hello User Secured With Role USER";
    }
}
