package com.project.ppppharmaciemicroservice.Controller;

import com.project.ppppharmaciemicroservice.DTO.MedecineDto;
import com.project.ppppharmaciemicroservice.DTO.StockDto;
import com.project.ppppharmaciemicroservice.DTO.StockDtoCreation;
import com.project.ppppharmaciemicroservice.Entity.*;
import com.project.ppppharmaciemicroservice.Exceptions.StockNotFoundException;
import com.project.ppppharmaciemicroservice.Repositories.MedecinRepo;
import com.project.ppppharmaciemicroservice.Repositories.StockRepo;
import com.project.ppppharmaciemicroservice.Services.MedecinService;
import com.project.ppppharmaciemicroservice.Services.StockService;
import lombok.AllArgsConstructor;
import org.jboss.resteasy.spi.UnauthorizedException;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/medecin")
@AllArgsConstructor
public class MedecinController {
    private final MedecinService medecinService;
    @Autowired
    MedecinRepo medecinRepo;
    @PostMapping("/add")
    public String addMedicine(@RequestBody MedecineDto medecineDto, KeycloakAuthenticationToken auth) {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        String username = context.getToken().getPreferredUsername();
        try {
            medecinService.compareAndAddMedicine(medecineDto,username);
            return "Medicine added successfully!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error reading Excel file!";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateStock(@PathVariable Long id, @RequestBody MedecineDto medecineDto, KeycloakAuthenticationToken auth) {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        String username = context.getToken().getPreferredUsername();
        try {
            medecinService.update(medecineDto, username,id);
            return ResponseEntity.ok("Medecine has been updated.");
        } catch (StockNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
    @GetMapping("/ids")
    public ResponseEntity<List<Long>> retrieveMedecinIdsByNames(@RequestParam List<String> medecinNames) {
        List<Long> medecinIds = medecinService.retrieveMedecinIdsByNames(medecinNames);
        return new ResponseEntity<>(medecinIds, HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Medicaments>> getAllMedicaments() {
        return medecinService.findAllMedecine();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Medicaments> getMedicamentsById(@PathVariable Long id) {
        return medecinService.getMedecineById(id);
    }
    @PostMapping("/search")
    public ResponseEntity<List<Medicaments>> searchMedicine(@RequestBody SearchMedecin searchProduct) {
        return medecinService.searchMedicine(searchProduct);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMedicament(@PathVariable Long id) {
        try {
            ResponseEntity<Medicaments> response = medecinService.deleteMedicament(id);
            HttpStatus status = response.getStatusCode();
            if (status == HttpStatus.OK) {
                return new ResponseEntity<>("Medicament deleted successfully.", HttpStatus.OK);
            } else if (status == HttpStatus.NOT_FOUND) {
                return new ResponseEntity<>("Medicament not found.", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>("An error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>("An error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/check_medicine/{medicineName}")
    public ResponseEntity<String> checkMedicine(@PathVariable String medicineName) {

        if (medicineName == null || medicineName.isEmpty()) {
            return ResponseEntity.badRequest().body("Medicine name is required");
        }

        boolean isAvailable = medecinService.isMedicineAvailable(medicineName);
        boolean isNotExpired = medecinService.isNotExpired(medicineName);

        if (isAvailable && isNotExpired) {
            return ResponseEntity.ok("Medicine is available and not expired");
        } else if (!isAvailable) {
            return ResponseEntity.ok("Medicine is not available in stock");
        } else {
            return ResponseEntity.ok("Medicine has expired");
        }
    }
    @GetMapping("/retrieve")
    public ResponseEntity<List<String>> retrieveMedicines() {
        try {
            List<String> medicines = medecinService.retrieveMedicinesFromExcel();
            return ResponseEntity.ok(medicines);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/getDose")
    public String getDose(@RequestParam String medicineName) {
        try {
            return medecinService.getDoseByName(medicineName);
        } catch (IOException e) {
            e.printStackTrace();
            return "Error reading data";
        }
    }

}
