package com.project.pppmagasinmicroservice.Controllers;

import com.project.pppmagasinmicroservice.Repositories.PostRepository;
import com.project.pppmagasinmicroservice.Services.PostService;
import com.project.pppmagasinmicroservice.Utilis.DTO.MessageResponse;
import com.project.pppmagasinmicroservice.Utilis.DTO.PostRequest;
import com.project.pppmagasinmicroservice.Utilis.DTO.PostResponse;
import com.project.pppmagasinmicroservice.Utilis.Exception.PostNotFoundException;
import lombok.AllArgsConstructor;
import org.jboss.resteasy.spi.UnauthorizedException;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor

public class PostController {
    private final PostService postService;
    @Autowired
    PostRepository postRepository;


    @PostMapping("/add")
    public ResponseEntity createPost(@RequestBody PostRequest postRequest, KeycloakAuthenticationToken auth) {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        String username = context.getToken().getPreferredUsername();
        postService.save(postRequest,username);
        return new ResponseEntity(HttpStatus.CREATED);

    }
    @PutMapping("/update/{pubId}")
    public ResponseEntity<String> updatePost(@PathVariable Long pubId, @RequestBody PostRequest postRequest, KeycloakAuthenticationToken auth) {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        String username = context.getToken().getPreferredUsername();
        try {
            postService.update(postRequest, username,pubId);
            return ResponseEntity.ok("Post has been updated.");
        } catch (PostNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return status(HttpStatus.OK).body(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        return status(HttpStatus.OK).body(postService.getPost(id));
    }
    @GetMapping("byUsername/{username}")
    public ResponseEntity<List<PostResponse>> getPostByUsername(@PathVariable String username) {
        return status(HttpStatus.OK).body(postService.getAllPostsByUsername(username));
    }

    @GetMapping("by-subplateforme/{id}")
    public ResponseEntity<List<PostResponse>> getPostsBySubplateforme(@PathVariable Long id) {
        return status(HttpStatus.OK).body(postService.getPostsBySubplateforme(id));

    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok(new MessageResponse("Post deleted successfully!"));
    }

}
