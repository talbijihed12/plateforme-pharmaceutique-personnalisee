package com.project.ppprapport.Controller;

import com.project.ppprapport.DTO.DossierDto;
import com.project.ppprapport.DTO.SearchDossier;
import com.project.ppprapport.Entity.Dossier;
import com.project.ppprapport.Exceptions.DossierNotFoundException;
import com.project.ppprapport.Services.DossierService;
import com.project.ppprapport.usermicroservice.UserDto;
import feign.RequestLine;
import lombok.AllArgsConstructor;
import org.jboss.resteasy.spi.UnauthorizedException;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/dossier")
@AllArgsConstructor
public class DossierController {
    @Autowired
    private UserDto userDto;
    private final DossierService dossierService;
    @Autowired
    private UsersResource usersResource;

    @GetMapping("/check")
    public boolean isPatient(@RequestParam String patientName) {
        return userDto.isPatient(patientName);
    }
    @PostMapping("/add")
    public ResponseEntity<String> createDossier(@RequestBody Dossier dossier, KeycloakAuthenticationToken auth) {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        String username = context.getToken().getPreferredUsername();
        try {
            Dossier createdDossier = dossierService.createDossier(dossier,username);
            return ResponseEntity.ok("Dossier created with ID: " + createdDossier.getId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }
    @GetMapping("/checkUserRole")
    public ResponseEntity<Boolean> checkUserRole(@RequestParam String username, @RequestParam String role) {
        boolean hasRole = hasRole(usersResource, username, role);
        return new ResponseEntity<>(hasRole, HttpStatus.OK);
    }

    private boolean hasRole(UsersResource usersResource, String username, String roleName) {
        List<UserRepresentation> userList = usersResource.search(username);

        if (!userList.isEmpty()) {
            UserRepresentation user = userList.get(0);

            List<RoleRepresentation> userRoles = usersResource.get(user.getId()).roles().realmLevel().listAll();
            return userRoles.stream().anyMatch(userRole -> userRole.getName().equals(roleName));
        }

        return false;
    }
    @GetMapping("/getMyDossier")
    public ResponseEntity<Dossier> getDossierForPatient( KeycloakAuthenticationToken auth) {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        String patientName = context.getToken().getPreferredUsername();
        Dossier dossier = dossierService.getDossierForPatient(patientName);
        if (dossier != null) {
            return ResponseEntity.ok(dossier);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateDossier(@PathVariable Long id, @RequestBody DossierDto dossierDto, KeycloakAuthenticationToken auth) {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        String username = context.getToken().getPreferredUsername();
        Set<String> roles= context.getToken().getRealmAccess().getRoles();
        boolean hasMedecinOrInfirmierRole = roles.contains("Infirmier") || roles.contains("Medecin");
        if (!hasMedecinOrInfirmierRole) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized to update dossier");
        }
        try {
            dossierService.update(dossierDto, username,id);
            return ResponseEntity.ok("Dossier has been updated.");
        } catch (DossierNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
    private boolean hasMedecinOrInfirmierRole(UsersResource usersResource, String username) {
        List<UserRepresentation> userList = usersResource.search(username);

        if (!userList.isEmpty()) {
            UserRepresentation user = userList.get(0);
            List<RoleRepresentation> userRoles = usersResource.get(user.getId()).roles().realmLevel().listAll();
            return userRoles.stream().anyMatch(userRole -> userRole.getName().equals("Medecin")|| userRole.getName().equals("Infirmier"));
        }

        return false;
    }
    @GetMapping("/all")
    public List<Dossier> getAllDossiers() {
        return dossierService.findAllDossier();
    }
    @GetMapping("/{id}")
    public Dossier getDossierById(@PathVariable Long id) {
        return dossierService.findDossierById(id);
    }
    @PostMapping("/search")
    public ResponseEntity<List<Dossier>> searchMedicine(@RequestBody SearchDossier searchDossier) {
        return dossierService.searchDossier(searchDossier);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMedicament(@PathVariable Long id) {
        try {
            ResponseEntity<Dossier> response = dossierService.deleteDossier(id);
            HttpStatus status = response.getStatusCode();
            if (status == HttpStatus.OK) {
                return new ResponseEntity<>("Dossier deleted successfully.", HttpStatus.OK);
            } else if (status == HttpStatus.NOT_FOUND) {
                return new ResponseEntity<>("Dossier not found.", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>("An error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>("An error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
