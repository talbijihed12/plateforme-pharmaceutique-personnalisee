package com.project.authentification.Controller;

import com.project.authentification.Payload.Request.KeycloakUser;
import com.project.authentification.Service.KeyCloakService;
import lombok.AllArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@AllArgsConstructor
@RequestMapping( "/pharmacien")
@CrossOrigin("*")
public class PharmacienController {
    KeyCloakService service;
    @GetMapping("/admin/findAllPharmaciens")
    public List<KeycloakUser> getPharmacyUsers(){
        List<KeycloakUser> user = service.getUsersWithRole();
        return user;
    }
    @GetMapping("/admin/details/{username}")
    public UserRepresentation getUserWithName(@PathVariable("username") String username){
        UserRepresentation user = service.getUserByName(username);
        return user;
    }
}
