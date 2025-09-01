package com.oauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.authorization.SingleResultAuthorizationManager.permitAll;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(
                    authorize -> {
                        authorize.requestMatchers("/","/hello").permitAll();
                        authorize.anyRequest().authenticated();
                    })
                .formLogin(form -> {
                          // para un login custom
                    form.defaultSuccessUrl("/helloSecured", true);
                    form.permitAll();
                })
                .oauth2Login(oauth2 -> {
                    oauth2.defaultSuccessUrl("/helloSecured", true);
                    oauth2.permitAll();
                });


        return http.build();
    }



}
