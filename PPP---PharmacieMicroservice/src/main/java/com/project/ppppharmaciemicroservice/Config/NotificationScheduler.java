package com.project.ppppharmaciemicroservice.Config;

import com.project.ppppharmaciemicroservice.usermicroservice.KeycloakUser;
import com.project.ppppharmaciemicroservice.Entity.Stock;
import com.project.ppppharmaciemicroservice.Services.NotificationService;
import com.project.ppppharmaciemicroservice.Services.StockService;
import com.project.ppppharmaciemicroservice.usermicroservice.UserDto;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

import static com.project.ppppharmaciemicroservice.Config.KeycloakConfig.realm;

@Configuration
@EnableScheduling
public class NotificationScheduler {
    @Autowired

    private UserDto userDto;
    private final StockService stockService;
    private final Keycloak keycloak;

    private final NotificationService notificationService;

    public NotificationScheduler(StockService stockService
            , Keycloak keycloak, NotificationService notificationService) {
        this.stockService = stockService;
        this.keycloak = keycloak;

        this.notificationService = notificationService;
    }

    @Scheduled(cron = "* * 8 * * *")
    public void checkSoonToExpireStock() {
        int thresholdDays = 7;
        List<Stock> soonToExpireStock = stockService.findSoonToExpireStock(thresholdDays);

        if (!soonToExpireStock.isEmpty()) {
            List<KeycloakUser> pharmacyUsers = userDto.getPharmacyUsers() ;
            for (KeycloakUser pharmacyUser : pharmacyUsers) {
                String email = pharmacyUser.getEmail();
                StringBuilder messageBuilder = new StringBuilder("Some stock items are expiring soon. Please check your inventory.\n");
                for (Stock stock : soonToExpireStock) {
                    messageBuilder.append("Stock ID: ").append(stock.getId()).append("\n");
                }
                notificationService.sendNotificationToEmail(email, String.valueOf(messageBuilder));
            }
        }

    }
    @Scheduled(cron = "* * 8 * * *")
    private List<UserRepresentation> findPharmacyUsers() {
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();
        RoleRepresentation pharmacyRole = realmResource.roles().get("Pharmacien").toRepresentation();
        return usersResource.search(null, null, pharmacyRole.getId(), null, null, null);
    }



}
