package com.project.ppprendezvous.Controller;

import com.project.ppprendezvous.Entity.ReservationMedicament;
import com.project.ppprendezvous.Repositories.ReservationRepo;
import com.project.ppprendezvous.Services.ReservationService;
import com.sun.media.sound.InvalidDataException;
import lombok.AllArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/reservation")
@AllArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    private static final String QR_CODE_IMAGE_PATH = "./src/main/resources/static/img/QRCode.png";
    @PostMapping("/addWithUsername")
    public ReservationMedicament addReservationMedicamentUsername(@RequestBody ReservationMedicament rm ,KeycloakAuthenticationToken auth) throws InvalidDataException {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        String username = context.getToken().getPreferredUsername();
        return reservationService.addReservation(rm,username);
    }
    @PostMapping("/add/{username}")
    public ReservationMedicament addReservationMedicament(@RequestBody ReservationMedicament rm, @PathVariable("username") String username, KeycloakAuthenticationToken auth) throws InvalidDataException {
        return reservationService.addReservation(rm,username);
    }

    @PostMapping("/add")
    public ReservationMedicament addReservationMedicament(@RequestBody ReservationMedicament rm) {
        return reservationService.addReservationMedicament(rm);
    }

    @GetMapping("/retrieveAll")
    public List<ReservationMedicament> getReservationMedicament() {
        List<ReservationMedicament> listReservations = reservationService.retrieveAllReservationMedicament();
        return listReservations;
    }
    @GetMapping("/retrieve/{id}")
    public ReservationMedicament retrieveReservationMedicament(@PathVariable("id") Long Id) {
        return reservationService.retrieveReservationMedicament(Id);
    }
    @DeleteMapping("/remove/{id}")
    public void removeReservationMedicament(@PathVariable("id") Long Id) {
        reservationService.deleteReservationMedicament(Id);
    }
    @PutMapping("/modify/{id}")
    public ReservationMedicament modifyReservationMedicament(@RequestBody ReservationMedicament rm, @PathVariable("id")Long id) throws InvalidDataException {
        return reservationService.updateReservationMedicament(rm,id);
    }

    @PutMapping("/approveReservationMedicament/{id}")
    public void approveReservationMedicament(@PathVariable("id") Long id) throws InvalidDataException {
        reservationService.approveReservationMedicament(id);
    }
    @DeleteMapping("/cancelReservationMedicament/{id}")
    public void cancelReservationFoyer(@PathVariable("id") Long id) {
        reservationService.cancelReservationMedicament(id);
    }

    @GetMapping("/retrieveReservationMedicamentByUserId")
    public List<ReservationMedicament> retrieveReservationMedicamentByUsername(KeycloakAuthenticationToken auth){
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        String username = context.getToken().getPreferredUsername();
        return reservationService.retrieveReservationMedicamentByUsername(username);
    }
    @GetMapping("/retrieveReservationMedicamentByUserId/{username}")
    public List<ReservationMedicament> retrieveReservationMedicamentByUsername(@PathVariable("username") String username){
        return reservationService.retrieveActiveReservationMedicamentByUsername(username);
    }
    @GetMapping("/retrieveActiveReservationMedicament")
    public List<ReservationMedicament> retrieveActiveReservationMedicament(){
        return reservationService.retrieveActiveReservationMedicament();
    }
    @GetMapping("/retrieveActiveReservationMedicamentByUsername/{username}")
    public List<ReservationMedicament> retrieveActiveReservationMedicamentByUserId(@PathVariable("username") String username){
        return reservationService.retrieveActiveReservationMedicamentByUsername(username);
    }
    @GetMapping("/retrieveActiveReservationMedicamentByUsername")
    public List<ReservationMedicament> retrieveActiveReservationMedicamentByUserId( KeycloakAuthenticationToken auth){
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        String username = context.getToken().getPreferredUsername();
        return reservationService.retrieveActiveReservationMedicamentByUsername(username);
    }
    @GetMapping(value="/getQRCodeWithUsername/{id}",produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[]  getQRCodeWithUsername(HttpServletResponse response, @PathVariable("id") Long id) throws IOException {
        String path = reservationService.GenerateQrCodeWithUsername(id);
        InputStream in = new FileInputStream(path);

        return IOUtils.toByteArray(in);
    }

}
