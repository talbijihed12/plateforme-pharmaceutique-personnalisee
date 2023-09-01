package com.project.ppprapport.Controller;

import com.project.ppprapport.DTO.OrdonnanceDto;
import com.project.ppprapport.DTO.RapportDto;
import com.project.ppprapport.DTO.SearchOrdonnance;
import com.project.ppprapport.DTO.SearchRapport;
import com.project.ppprapport.Entity.Ordonnance;
import com.project.ppprapport.Entity.Rapport;
import com.project.ppprapport.Exceptions.DossierNotFoundException;
import com.project.ppprapport.Services.RapportService;
import com.sun.media.sound.InvalidDataException;
import lombok.AllArgsConstructor;
import org.jboss.resteasy.spi.UnauthorizedException;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/rapport")
@AllArgsConstructor
public class RapportController {
    private final RapportService rapportService;
    @PostMapping("/add")
    public ResponseEntity<String> createRapport(@ModelAttribute Rapport rapport, @RequestParam("files") List<MultipartFile> files, KeycloakAuthenticationToken auth) {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        String username = context.getToken().getPreferredUsername();
        try {
            rapportService.addRapport(files,rapport,username);
            return new ResponseEntity<>("Rapport created successfully", HttpStatus.CREATED);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/getMyRapport")
    public ResponseEntity<List<Rapport>> getRapportForPatient(KeycloakAuthenticationToken auth) {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        String patientName = context.getToken().getPreferredUsername();
        List<Rapport> rapports = rapportService.findRapportByNomMalade(patientName);
        if (rapports != null) {
            return ResponseEntity.ok(rapports);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public List<Rapport> getAllRapports(KeycloakAuthenticationToken auth) {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        String medecin = context.getToken().getPreferredUsername();
        return rapportService.findAllRapports(medecin);
    }
    @GetMapping("/{id}")
    public Rapport getRapportById(@PathVariable Long id,KeycloakAuthenticationToken auth) {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        String medecin = context.getToken().getPreferredUsername();
        return rapportService.findRapportById(id,medecin);
    }
    @PostMapping("/search")
    public ResponseEntity<List<Rapport>> searchRapport(@RequestBody SearchRapport searchRapport) {
        return rapportService.searchRapports(searchRapport);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRapport(@PathVariable Long id) {
        try {
            ResponseEntity<Rapport> response = rapportService.deleteRapport(id);
            HttpStatus status = response.getStatusCode();
            if (status == HttpStatus.OK) {
                return new ResponseEntity<>("Rapport deleted successfully.", HttpStatus.OK);
            } else if (status == HttpStatus.NOT_FOUND) {
                return new ResponseEntity<>("Rapport not found.", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>("An error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>("An error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateRapport(@PathVariable Long id, @RequestBody RapportDto rapportDto, KeycloakAuthenticationToken auth) {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        String username = context.getToken().getPreferredUsername();
        Set<String> roles= context.getToken().getRealmAccess().getRoles();
        boolean hasMedecinOrInfirmierRole = roles.contains("Medecin");
        if (!hasMedecinOrInfirmierRole) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized to update Rapport");
        }
        try {
            rapportService.update(rapportDto, username,id);
            return ResponseEntity.ok("Rapport has been updated.");
        } catch (DossierNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (InvalidDataException e) {
            throw new RuntimeException(e);
        }
    }

}
