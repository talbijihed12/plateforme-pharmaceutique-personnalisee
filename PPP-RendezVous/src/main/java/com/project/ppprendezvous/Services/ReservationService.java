package com.project.ppprendezvous.Services;

import com.google.zxing.WriterException;
import com.project.ppprendezvous.EmailService.EmailService;
import com.project.ppprendezvous.Entity.ReservationMedicament;
import com.project.ppprendezvous.Repositories.ReservationRepo;
import com.project.ppprendezvous.Utils.QRCodeGenerator;
import com.project.ppprendezvous.config.KeycloakConfig;
import com.project.ppprendezvous.pharmacieMicroservice.MedecinServices;
import com.project.ppprendezvous.usermicroservice.KeycloakUser;
import com.project.ppprendezvous.usermicroservice.UserDto;
import com.sun.media.sound.InvalidDataException;
import lombok.AllArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static java.lang.System.currentTimeMillis;

@Service
@AllArgsConstructor
public class ReservationService implements IReservervationService{
    @Autowired
    private UserDto userDto;
    private MedecinServices medecinServices;
    private final ReservationRepo reservationRepo;
    private final EmailService emailService;
    @Override
    public ReservationMedicament addReservation(ReservationMedicament rm, String username) throws InvalidDataException {
        List<String> medicinesFromMicroservice = medecinServices.retrieveMedicines();
        String[] medications = rm.getNameMedicament().split(",");
        for (String medication : medications) {
            String trimmedMedication = medication.trim();
            if (!medicinesFromMicroservice.contains(trimmedMedication)) {
                throw new InvalidDataException("Invalid medicament: " + trimmedMedication);
            }
        }
        ResponseEntity<List<Long>> response = medecinServices.retrieveMedecinIdsByNames(Arrays.asList(medications));
        if (response.getStatusCode() == HttpStatus.OK) {
            List<Long> medecinIds = response.getBody();
            rm.setUsername(username);
            rm.setNameMedicament(rm.getNameMedicament());
            rm.setMedecinIdList(medecinIds);

            return reservationRepo.save(rm);
        } else {
            throw new RuntimeException("Failed to retrieve Medecin IDs");
        }
    }
    @Override
    public ReservationMedicament addReservationMedicament(ReservationMedicament rm) {
        return reservationRepo.save(rm);
    }
    @Override
    public void deleteReservationMedicament(Long id) {
        reservationRepo.deleteById(id);
    }

    @Override
    public ReservationMedicament updateReservationMedicament(ReservationMedicament rm, Long id) throws InvalidDataException {
        List<String> medicinesFromMicroservice = medecinServices.retrieveMedicines();
        String[] medications = rm.getNameMedicament().split(",");
        for (String medication : medications) {
            String trimmedMedication = medication.trim();
            if (!medicinesFromMicroservice.contains(trimmedMedication)) {
                throw new InvalidDataException("Invalid medicament: " + trimmedMedication);
            }
        }
        ResponseEntity<List<Long>> response = medecinServices.retrieveMedecinIdsByNames(Arrays.asList(medications));
        if (response.getStatusCode() == HttpStatus.OK) {
            List<Long> medecinIds = response.getBody();
            rm.setNameMedicament(rm.getNameMedicament());
            rm.setMedecinIdList(medecinIds);
            return reservationRepo.save(rm);
        }
        if (rm.getUsername() != null) {
            UserRepresentation userDTO =userDto.getUserWithName(rm.getUsername());
            String subject = "PPP--Application Reservation updated !";
            String text = "Hello," +userDTO.getUsername()+"!\n\n"
                    + "This is an email to inform you that your Reservation in :  "
                    + rm.getDate().toString()
                    + " For drug :"
                    + rm.getNameMedicament().toString()
                    + " has been updated !"
                    +"\n\n"
                    + "We hope you're having a great day!\n\n"
                    + "Best regards,\n";
            emailService.sendSimpleMessage(userDTO.getEmail(),subject,text);

        }
        return rm;
    }

    @Override
    public ReservationMedicament retrieveReservationMedicament(Long id) {
        ReservationMedicament rm= reservationRepo.findById(id).get();
        return rm;
    }

    @Override
    public List<ReservationMedicament> retrieveAllReservationMedicament() {
        List<ReservationMedicament> listReservationMedicament ;
        listReservationMedicament=reservationRepo.findAll();
        for (ReservationMedicament rm : listReservationMedicament) {
            if (rm.getDate().before(new Date(currentTimeMillis()))) {
                rm.setExpired(true);
                reservationRepo.save(rm);
            }
        }
        return listReservationMedicament;

    }

    @Override
    public void approveReservationMedicament(Long id) throws InvalidDataException {
        ReservationMedicament rm = retrieveReservationMedicament(id);
        rm.setApproved(true);
        if (rm.getUsername() != null) {
            KeycloakUser userDTO = new KeycloakUser();
            List<UserRepresentation> aa = KeycloakConfig.getInstance().realm("plateforme-pharmaceutique").users()
                    .search(rm.getUsername());
            for (UserRepresentation a:aa) {
                userDTO.setEmail(a.getEmail());
                userDTO.setUsername(a.getUsername());
            }
            String subject = "Medecin Reservation approved !";
            String text = "Hello, " + userDTO.getUsername() +"\n\n"
                    + "This is an email to inform you that your demand for an Medecin Reservation in : "
                    + rm.getDate().toString()
                    + " For :"
                    + rm.getNameMedicament().toString()
                    + " has been approved !"
                    + "\n\n"
                    + "Best regards,\n";

            emailService.sendSimpleMessage(userDTO.getEmail(), subject, text);
        }
    }

    @Override
    public void cancelReservationMedicament(Long id) {
        ReservationMedicament rm = retrieveReservationMedicament(id);
        List<Long> listRoom = rm.getMedecinIdList();
        for (Long idR : listRoom) {
            medecinServices.getMedicamentsById(idR);
        }
        deleteReservationMedicament(id);
        if (rm.getUsername() != null) {
            UserRepresentation userDTO = userDto.getUserWithName(rm.getUsername());
            String subject = "Medecin Reservation cancelled !";
            String text = "Hello, " + userDTO.getUsername() +"\n\n"
                    + "This is an email to inform you that your demand for Medecin Reservation in : "
                    + rm.getDate().toString()
                    + " for drug :"
                    + rm.getNameMedicament().toString()
                    + " has been cancelled !"
                    + "\n\n"
                    + "Best regards,\n";
            emailService.sendSimpleMessage(userDTO.getEmail(), subject, text);
        }
    }
    @Override
    public List<ReservationMedicament> retrieveReservationMedicamentByUsername(String username) {
        return reservationRepo.findReservationMedicamentByUsername(username);
    }

    @Override
    public List<ReservationMedicament> retrieveActiveReservationMedicament() {
        return reservationRepo.findReservationMedicamentsByExpired(false);
    }

    @Override
    public List<ReservationMedicament> retrieveActiveReservationMedicamentByUsername(String username) {
        return reservationRepo.findReservationMedicamentsByExpiredAndUsername(false,username);
    }

    @Override
    public String GenerateQrCodeWithUsername(Long id) {
        final String QR_CODE_IMAGE_PATH = "./src/main/resources/static/img/"+id.toString()+".png";

        ReservationMedicament rm=reservationRepo.findById(id).get();


        String text = rm.getUsername()
                + "\n"
                + rm.getNameMedicament()
                +"\n"
                +rm.getDate().toString()
                +"\n"
                +rm.getApproved()
                +"\n"
                +rm.getExpired()
                +"\n";

        try {
            // Generate and Save Qr Code Image in static/image folder
            QRCodeGenerator.generateQRCodeImage(text,250,250,QR_CODE_IMAGE_PATH);

        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }

        return QR_CODE_IMAGE_PATH;
    }

    @Override
    public String GenerateQrCodeWithoutUsername(Long id){
        final String QR_CODE_IMAGE_PATH = "./src/main/resources/static/img/"+id.toString()+".png";

        ReservationMedicament rm=reservationRepo.findById(id).get();


        String text = rm.getUsername()
                + "\n"
                + rm.getNameMedicament()
                +"\n"
                +rm.getDate().toString()
                +"\n"
                +rm.getApproved()
                +"\n"
                +rm.getExpired()
                +"\n";
        try {
            // Generate and Save Qr Code Image in static/image folder
            QRCodeGenerator.generateQRCodeImage(text,250,250,QR_CODE_IMAGE_PATH);

        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }

        return QR_CODE_IMAGE_PATH;
    }

}
