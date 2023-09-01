package com.project.ppprapport.Controller;

import com.project.ppprapport.Config.Notification;
import com.project.ppprapport.DTO.DossierDto;
import com.project.ppprapport.DTO.OrdonnanceDto;
import com.project.ppprapport.DTO.SearchDossier;
import com.project.ppprapport.DTO.SearchOrdonnance;
import com.project.ppprapport.Entity.Dossier;
import com.project.ppprapport.Entity.Ordonnance;
import com.project.ppprapport.Exceptions.DossierNotFoundException;
import com.project.ppprapport.Services.NotificationService;
import com.project.ppprapport.Services.OrdonnanceService;
import com.sun.media.sound.InvalidDataException;
import lombok.AllArgsConstructor;
import org.jboss.resteasy.spi.UnauthorizedException;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/Ordonnance")
@AllArgsConstructor
public class OrdonnanceController {
    private final OrdonnanceService ordonnanceService;
    private final Notification notification;

    @PostMapping("/add")
    public ResponseEntity<String> createDossier(@RequestBody Ordonnance ordonnance, KeycloakAuthenticationToken auth) {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        String username = context.getToken().getPreferredUsername();
        try {
            ordonnanceService.createOrdonnance(ordonnance, username);
            return new ResponseEntity<>("Ordonnance created successfully", HttpStatus.CREATED);
        } catch (InvalidDataException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/send-notification")
    public ResponseEntity<String> sendNotification(@RequestParam("nomMalade") String nomMalade, @RequestParam("pharmacienName") String pharmacienName) {
        try {
            notification.sendOrdonnanceToPharmacien(nomMalade, pharmacienName);
            return ResponseEntity.ok("Notification sent successfully.");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateOrdonnance(@PathVariable Long id, @RequestBody OrdonnanceDto ordonnanceDto, KeycloakAuthenticationToken auth) {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        String username = context.getToken().getPreferredUsername();
        Set<String> roles= context.getToken().getRealmAccess().getRoles();
        boolean hasMedecinOrInfirmierRole = roles.contains("Medecin");
        if (!hasMedecinOrInfirmierRole) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized to update Ordonnance");
        }
        try {
            ordonnanceService.update(ordonnanceDto, username,id);
            return ResponseEntity.ok("Ordonnance has been updated.");
        } catch (DossierNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (InvalidDataException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/getMyOrdonnance")
    public ResponseEntity<List<Ordonnance>> getOrdonnanceForPatient(KeycloakAuthenticationToken auth) {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        String patientName = context.getToken().getPreferredUsername();
        List<Ordonnance> ordonnances = ordonnanceService.getOrdonnanceForPatient(patientName);
        if (ordonnances != null) {
            return ResponseEntity.ok(ordonnances);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public List<Ordonnance> getAllOrdonnance(KeycloakAuthenticationToken auth) {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        String medecin = context.getToken().getPreferredUsername();
        return ordonnanceService.findAllOrdonnance(medecin);
    }
    @GetMapping("/{id}")
    public Ordonnance getOrdonnanceById(@PathVariable Long id,KeycloakAuthenticationToken auth) {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        String medecin = context.getToken().getPreferredUsername();
        return ordonnanceService.findOrdonnanceById(id,medecin);
    }
    @PostMapping("/search")
    public ResponseEntity<List<Ordonnance>> searchMedicine(@RequestBody SearchOrdonnance searchOrdonnance) {
        return ordonnanceService.searchOrdonnance(searchOrdonnance);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMedicament(@PathVariable Long id) {
        try {
            ResponseEntity<Ordonnance> response = ordonnanceService.deleteOrdonnance(id);
            HttpStatus status = response.getStatusCode();
            if (status == HttpStatus.OK) {
                return new ResponseEntity<>("Ordonance deleted successfully.", HttpStatus.OK);
            } else if (status == HttpStatus.NOT_FOUND) {
                return new ResponseEntity<>("Ordonance not found.", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>("An error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>("An error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/medecins/{nomMalade}")
    public List<String> getDrugsForPatient(@PathVariable String nomMalade) {
        return ordonnanceService.getMedecinsForPatient(nomMalade);
    }
}
