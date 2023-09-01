package com.project.pppmagasinmicroservice.Controllers;


import com.project.pppmagasinmicroservice.Services.CommentService;
import com.project.pppmagasinmicroservice.Utilis.DTO.CommentTO;
import com.project.pppmagasinmicroservice.Utilis.DTO.CommentToResponse;
import com.project.pppmagasinmicroservice.Utilis.DTO.MessageResponse;
import com.project.pppmagasinmicroservice.Utilis.DTO.PostRequest;
import com.project.pppmagasinmicroservice.Utilis.Exception.CommentNotFoundException;
import com.project.pppmagasinmicroservice.Utilis.Exception.PostNotFoundException;
import lombok.AllArgsConstructor;
import org.jboss.resteasy.spi.UnauthorizedException;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/comments")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentTO commentTO, KeycloakAuthenticationToken auth) {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        String username = context.getToken().getPreferredUsername();
        commentService.save(commentTO,username);
        return new ResponseEntity<>(CREATED);

    }
    @PutMapping("/update/{idComment}")
    public ResponseEntity<String> updatePost(@PathVariable Long idComment, @RequestBody CommentTO commentTO, KeycloakAuthenticationToken auth) {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        String username = context.getToken().getPreferredUsername();
        try {
            commentService.update(commentTO, username,idComment);
            return ResponseEntity.ok("Comment has been updated.");
        } catch (CommentNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
    @GetMapping("{idComment}")
    public ResponseEntity<CommentToResponse> getComment(@PathVariable Long idComment) {
        return status(HttpStatus.OK).body(commentService.getComment(idComment));
    }


    @GetMapping("/by-post/{postId}")
    public ResponseEntity<List<CommentToResponse>> getAllCommentsForPost(@PathVariable Long postId) {
        return ResponseEntity.status(OK).body(commentService.getAllCommentsForPost(postId));

    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok(new MessageResponse("Comment deleted successfully!"));
    }
}
