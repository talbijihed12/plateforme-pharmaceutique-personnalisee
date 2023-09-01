package com.project.authentification.Controller;

import com.project.authentification.Service.KeyCloakService;
import lombok.AllArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.PathParam;

@RestController
@AllArgsConstructor
@RequestMapping( "/patient")
@CrossOrigin("*")
public class ClientCheckController {
    private final KeyCloakService keyCloakService;
    @GetMapping("/check")
    public boolean isPatient(@RequestParam("patientName") String patientName) {
        return keyCloakService.isPatient(patientName);
    }

}
