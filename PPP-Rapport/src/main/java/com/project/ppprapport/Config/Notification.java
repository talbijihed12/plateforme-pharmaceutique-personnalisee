package com.project.ppprapport.Config;

import com.project.ppprapport.Entity.Ordonnance;
import com.project.ppprapport.Services.NotificationService;
import com.project.ppprapport.Services.OrdonnanceService;
import com.project.ppprapport.usermicroservice.KeycloakUser;
import com.project.ppprapport.usermicroservice.UserDto;
import org.keycloak.admin.client.Keycloak;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

@Configuration
@EnableScheduling
public class Notification {
    @Autowired
    private UserDto userDto;
    private final Keycloak keycloak;
    private final OrdonnanceService ordonnanceService;

    private final NotificationService notificationService;

    public Notification(UserDto userDto, Keycloak keycloak, OrdonnanceService ordonnanceService, NotificationService notificationService) {
        this.userDto = userDto;
        this.keycloak = keycloak;
        this.ordonnanceService = ordonnanceService;
        this.notificationService = notificationService;
    }

    public void sendOrdonnanceToPharmacien(String nomMalade, String pharmacienName) {
        List<Ordonnance> relevantOrdonnance = ordonnanceService.findOrdonnanceByNomMalade(nomMalade);

        if (relevantOrdonnance != null) {
            KeycloakUser pharmacienUser = userDto.getPharmacyUsers().stream()
                    .filter(user -> user.getUsername().equalsIgnoreCase(pharmacienName))
                    .findFirst()
                    .orElse(null);

            if (pharmacienUser != null) {
                String email = pharmacienUser.getEmail();
                StringBuilder messageBuilder = new StringBuilder("You have received an ordonnance:\n");
                for (Ordonnance currentOrdonnance : relevantOrdonnance) {
                messageBuilder.append("Patient: ").append(currentOrdonnance.getNomMalade()).append("\n");
                messageBuilder.append("Ref Administrative: ").append(currentOrdonnance.getRefAdministrative()).append("\n");
                messageBuilder.append("Regime Social: ").append(currentOrdonnance.getRegimeSocial()).append("\n");
                messageBuilder.append("Code Cnam: ").append(currentOrdonnance.getCodeCnam()).append("\n");
                messageBuilder.append("Medecin: ").append(currentOrdonnance.getCreatedBy()).append("\n");
                messageBuilder.append("Date: ").append(currentOrdonnance.getDate()).append("\n");
                messageBuilder.append("Medicament: ").append(currentOrdonnance.getMedicament()).append("\n");
                messageBuilder.append("Etiquette: ").append(currentOrdonnance.getEtiquette()).append("\n");
                messageBuilder.append("Description: ").append(currentOrdonnance.getDescription()).append("\n");
                notificationService.sendNotificationToEmail(email, messageBuilder.toString());
                }
            } else {
                throw new IllegalArgumentException("Pharmacien Name does not exist");

            }
        } else {
            throw new IllegalArgumentException("No Ordonance under Nom Malade");

        }
    }

}
