package com.project.pppmagasinmicroservice.Controllers;

import com.project.pppmagasinmicroservice.Services.TopicService;
import com.project.pppmagasinmicroservice.Utilis.DTO.TopicDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/Topic")
@AllArgsConstructor
@Slf4j
public class TopicController {
    private final TopicService topicService;

    @PostMapping("/add")
    public ResponseEntity<TopicDto> createTopic(@RequestBody TopicDto topicDto, KeycloakAuthenticationToken auth) {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        String username = context.getToken().getPreferredUsername();
        return ResponseEntity.status(HttpStatus.CREATED).body(topicService.save(topicDto,username));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicDto> getTopic(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(topicService.getTopic(id));
    }

    @GetMapping
    public ResponseEntity<List<TopicDto>> getAllTopics() {
        return ResponseEntity.status(HttpStatus.OK).body(topicService.getAll());
    }
}
