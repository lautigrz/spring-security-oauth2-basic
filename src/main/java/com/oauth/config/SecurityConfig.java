package com.oauth.config;

import com.oauth.config.filter.JwtAuthorizationFilter;
import com.oauth.config.filter.OAuth2JwtSuccessHandler;
import com.oauth.service.UserDetailsServiceImpl;
import com.oauth.service.UserServiceOAuth2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.authorization.SingleResultAuthorizationManager.permitAll;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private OAuth2JwtSuccessHandler jwtSuccessHandler;

    @Autowired
    private UserServiceOAuth2 userServiceOAuth2;


    @Autowired
    private JwtAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(
                    authorize -> {
                        authorize.requestMatchers("/","/hello").permitAll();
                        authorize.anyRequest().authenticated();
                    })
                .formLogin(form -> {

                    form.defaultSuccessUrl("/helloSecured", true);
                    form.permitAll();
                })
                .oauth2Login(oauth2 -> {
                    oauth2.userInfoEndpoint(
                            userInfo -> userInfo.userService(userServiceOAuth2));
                    oauth2.successHandler(jwtSuccessHandler);

                })
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);




        return http.build();
    }



}
