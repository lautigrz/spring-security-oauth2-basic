package com.oauth.service;

import com.nimbusds.jose.shaded.gson.JsonElement;
import com.nimbusds.jose.shaded.gson.JsonParser;
import com.oauth.models.ERole;
import com.oauth.models.RoleEntity;
import com.oauth.models.UserEntity;
import com.oauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceOAuth2 implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        DefaultOAuth2UserService defaultOAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = defaultOAuth2UserService.loadUser(userRequest);


        String token = userRequest.getAccessToken().getTokenValue();
        String name = oAuth2User.getAttribute("login");

        UserEntity userEntity = userRepository.findByUsername(name)
                .orElseGet(() -> {
                    UserEntity newUser = new UserEntity();
                    newUser.setUsername(name);
                    newUser.setEmail(extractEmail(token));
                    newUser.setRoles(
                            Set.of(RoleEntity.builder()
                                    .role(ERole.USER)
                                    .build())
                    );
                    return userRepository.save(newUser);
                });


        return oAuth2User;
    }


    private String extractEmail(String token) {
        HttpClient client = HttpClient.newHttpClient();
        try {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.github.com/user/emails"))
                    .GET()
                    .headers("Authorization", "Bearer " + token)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() == 200) {
                JsonElement element = JsonParser.parseString(response.body());
                if (element.isJsonArray() && !element.getAsJsonArray().isEmpty()) {
                    return element.getAsJsonArray().get(0).getAsJsonObject().get("email").getAsString();
                }
            }

        } catch (IOException | InterruptedException e) {
            ((Throwable) e).printStackTrace();
        }
        return null;
    }

}
