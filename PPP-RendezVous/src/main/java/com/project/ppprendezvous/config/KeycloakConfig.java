package com.project.ppprendezvous.config;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.client.KeycloakClientRequestFactory;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {

    @Bean
    public KeycloakSpringBootConfigResolver keycloakSpringBootConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }
    @Bean
    KeycloakRestTemplate keycloakRestTemplate(KeycloakClientRequestFactory keycloakClientRequestFactory){
        return new KeycloakRestTemplate(keycloakClientRequestFactory);
    }

    static Keycloak keycloak;
    final static String serverUrl = "http://keycloak:8180/auth";
    public final static String realm = "plateforme-pharmaceutique";
    final static String clientId = "PPP-RendezVous";
    final static String clientSecret = "d021673c-f70c-4475-8d69-88b919b8bd87";
    final static String userName = "userr";
    final static String password = "userr";
    public KeycloakConfig() {
    }
@Bean
    public static Keycloak getInstance() {
    keycloak = KeycloakBuilder.builder()
            .serverUrl(serverUrl)
            .realm(realm)
            .grantType(OAuth2Constants.PASSWORD)
            .username(userName)
            .password(password)
            .clientId(clientId)
            .clientSecret(clientSecret)
            .resteasyClient(new ResteasyClientBuilder()
                    .connectionPoolSize(10)
                    .build())
            .build();
    return keycloak;
}




}



