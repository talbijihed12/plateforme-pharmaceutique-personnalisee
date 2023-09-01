package com.project.authentification.Service;


import com.project.authentification.Config.Credentials;
import com.project.authentification.Config.KeycloakConfig;
import com.project.authentification.Payload.Request.KeycloakUser;
import com.project.authentification.Repositories.VerificationTokenRepo;
import com.project.authentification.exceptions.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.project.authentification.Config.Credentials.createPasswordCredentials;


@Service
@AllArgsConstructor
@Slf4j
@EnableScheduling
public class KeyCloakService {

    private Keycloak keycloak;

    private final Logger logger = LoggerFactory.getLogger(getClass());


    public UserRepresentation addUser(KeycloakUser keycloakUser) {
        UsersResource usersResource = keycloak.realm(KeycloakConfig.realm).users();
        RealmResource realmResource = keycloak.realm(KeycloakConfig.realm);
        RolesResource rolesResource = realmResource.roles();

        CredentialRepresentation credentialRepresentation = createPasswordCredentials(keycloakUser.getPassword());
        UserRepresentation kcUser = new UserRepresentation();
        kcUser.setUsername(keycloakUser.getUsername());
        kcUser.setCredentials(Collections.singletonList(credentialRepresentation));
        kcUser.setFirstName(keycloakUser.getFirstName());
        kcUser.setLastName(keycloakUser.getLastName());
        kcUser.setEmail(keycloakUser.getEmail());
        kcUser.setEnabled(true);
        kcUser.setEmailVerified(false);
        kcUser.setRealmRoles(Collections.singletonList("Patient"));
        String enumValueAsString = keycloakUser.getGender().toString();
        kcUser.singleAttribute("gender", enumValueAsString);

        if (usersResource.search(keycloakUser.getUsername()).size() > 0) {
            throw new BadRequestException("User already exists");
        }

        // Create the user
        Response response = usersResource.create(kcUser);
        if (response.getStatus() != Response.Status.CREATED.getStatusCode()) {
            throw new InternalServerErrorException("Failed to create user");
        }

        String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");

        // Assign realm roles to the user
        RoleRepresentation role = rolesResource.get("Patient").toRepresentation(); // Replace "Patient" with the actual role name
        usersResource.get(userId).roles().realmLevel().add(Collections.singletonList(role));

        UsersResource instance = getInstance();
        instance.create(kcUser);

        return kcUser;
    }

    public UserRepresentation addUserByAdmin(KeycloakUser keycloakUser) {
        RealmResource realmResource = keycloak.realm(KeycloakConfig.realm);
        UsersResource usersResource = keycloak.realm(KeycloakConfig.realm).users();
        RolesResource rolesResource = realmResource.roles();
        CredentialRepresentation credentialRepresentation = createPasswordCredentials(keycloakUser.getPassword());
        UserRepresentation kcUser = new UserRepresentation();
        kcUser.setUsername(keycloakUser.getUsername());
        kcUser.setCredentials(Collections.singletonList(credentialRepresentation));
        kcUser.setFirstName(keycloakUser.getFirstName());
        kcUser.setLastName(keycloakUser.getLastName());
        kcUser.setEmail(keycloakUser.getEmail());
        kcUser.setEnabled(true);
        kcUser.setEmailVerified(false);
        kcUser.setRealmRoles(Collections.singletonList(keycloakUser.getRole()));
        String enumValueAsString = keycloakUser.getGender().toString();
        kcUser.singleAttribute("gender", enumValueAsString);
        if (usersResource.search(keycloakUser.getUsername()).size() > 0) {
            throw new BadRequestException("User already Exist");
        }
        Response response = usersResource.create(kcUser);
        if (response.getStatus() != Response.Status.CREATED.getStatusCode()) {
            throw new InternalServerErrorException("Failed to create user");
        }

        String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
        RoleRepresentation role = rolesResource.get(keycloakUser.getRole()).toRepresentation(); // Replace "Patient" with the actual role name
        usersResource.get(userId).roles().realmLevel().add(Collections.singletonList(role));
        UsersResource instance = getInstance();
        instance.create(kcUser);
        return kcUser;
    }

    public UsersResource getInstance() {
        return KeycloakConfig.getInstance().realm(KeycloakConfig.realm).users();
    }


    public UserRepresentation getUser(String userId) {
        UserRepresentation user = keycloak.realm("plateforme-pharmaceutique")
                .users().get(userId).toRepresentation();
        return user;

    }
    public UserRepresentation getUserwithName(String username) {
        UserRepresentation user = keycloak.realm("plateforme-pharmaceutique")
                .users().get(username).toRepresentation();
        return user;

    }

    public List<UserRepresentation> getPharmacyUsers() {
        RealmResource realmResource = keycloak.realm("plateforme-pharmaceutique");
        UsersResource usersResource = realmResource.users();
        RoleRepresentation pharmacyRole = realmResource.roles().get("Pharmacien").toRepresentation();
        System.out.println("Pharmacy Role ID: " + pharmacyRole.getId());
        System.out.println("Realm Resource: " + realmResource);
        System.out.println("Users Resource: " + usersResource);
        try {
            return usersResource.search(null, null, pharmacyRole.getId(), null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }

    }

// RealmResource realmResource = keycloak.realm("plateforme-pharmaceutique");
  //  RoleResource roleResource = realmResource.roles().get("Patient");
//@Scheduled(cron = "0 * * * * *")
        public List<KeycloakUser> getUsersWithRole() {
            RealmResource realmResource = keycloak.realm("plateforme-pharmaceutique");
            RoleRepresentation roleRepresentation = getRoleRepresentation(realmResource, "Pharmacien");
            if (roleRepresentation == null) {
                return Collections.emptyList();
            }

            List<KeycloakUser> usersWithRole = new ArrayList<>();
            UsersResource usersResource = realmResource.users();

            List<UserRepresentation> allUsers = usersResource.list();

            for (UserRepresentation user : allUsers) {
                if (hasRole(usersResource, user, roleRepresentation)) {
                    KeycloakUser keycloakUser = new KeycloakUser();
                    keycloakUser.setEmail(user.getEmail());
                    keycloakUser.setUsername(user.getUsername());
                    usersWithRole.add(keycloakUser);
                }
            }

            return usersWithRole;
        }

    private boolean hasRoles(UsersResource usersResource, UserRepresentation user, String roleName) {
        List<RoleRepresentation> userRoles = usersResource.get(user.getId()).roles().realmLevel().listAll();
        return userRoles.stream().anyMatch(userRole -> userRole.getName().equals(roleName));
    }

    public boolean isPatient(String patientName) {
        RealmResource realmResource = keycloak.realm(KeycloakConfig.realm);
        UsersResource usersResource = realmResource.users();

        List<UserRepresentation> users = usersResource.search(patientName);

        for (UserRepresentation user : users) {
            if (hasRoles(usersResource, user, "Patient")) {
                return true;
            }
        }

        return false;
    }



    private RoleRepresentation getRoleRepresentation(RealmResource realmResource, String roleName) {
        List<RoleRepresentation> allRoles = realmResource.roles().list();
        for (RoleRepresentation role : allRoles) {
            if (role.getName().equals(roleName)) {
                return role;
            }
        }
        return null;
    }

    private boolean hasRole(UsersResource usersResource, UserRepresentation user, RoleRepresentation role) {
        List<RoleRepresentation> userRoles = usersResource.get(user.getId()).roles().realmLevel().listAll();
        return userRoles.stream().anyMatch(userRole -> userRole.getName().equals(role.getName()));
    }

    public UserRepresentation getUserByName(String username) {
        UserRepresentation user = keycloak.realm("plateforme-pharmaceutique")
                .users().search(username).stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("User not found"));
        return user;

    }
   // @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserRepresentation> getUsers() {
        UsersResource usersResource = getInstance();
        List<UserRepresentation> user = usersResource.list();
        return user;

    }

    public void updateUserByAmin(String userId, KeycloakUser keycloakUser) {
        CredentialRepresentation credential = createPasswordCredentials(keycloakUser.getPassword());
        UserRepresentation user = new UserRepresentation();
        user.setUsername(keycloakUser.getUsername());
        user.setFirstName(keycloakUser.getFirstName());
        user.setLastName(keycloakUser.getLastName());
        user.setEmail(keycloakUser.getEmail());
        user.setCredentials(Collections.singletonList(credential));

        UsersResource usersResource = getInstance();
        usersResource.get(userId).update(user);
    }

    public void updateUser(String userId, KeycloakUser keycloakUser) {
        CredentialRepresentation credential = createPasswordCredentials(keycloakUser.getPassword());
        UserRepresentation user = new UserRepresentation();
        user.setUsername(keycloakUser.getUsername());
        user.setFirstName(keycloakUser.getFirstName());
        user.setLastName(keycloakUser.getLastName());
        user.setEmail(keycloakUser.getEmail());
        user.setCredentials(Collections.singletonList(credential));

        UsersResource usersResource = getInstance();
        usersResource.get(userId).update(user);
    }


    public void deleteUser(String userId) {
        UsersResource usersResource = getInstance();
        usersResource.get(userId)
                .remove();
    }
    public void deleteMyAccount(String userId) {
        UsersResource usersResource = getInstance();
        usersResource.get(userId)
                .remove();
    }


    public void assignRealmRolesToUser(KeycloakUser keycloakUser, String userName) {
        UsersResource usersResource = keycloak.realm(KeycloakConfig.realm).users();
        UserRepresentation user = usersResource.search(userName).get(0);
         usersResource = keycloak.realm(KeycloakConfig.realm).users();
         user = usersResource.search(userName).get(0);
        RoleRepresentation role = keycloak.realm(KeycloakConfig.realm).roles().get(keycloakUser.getRole()).toRepresentation();
        RoleMappingResource roleMappingResource = usersResource.get(user.getId()).roles();
        roleMappingResource.realmLevel().add(Collections.singletonList(role));

        List<RoleRepresentation> assignedRoles = roleMappingResource.realmLevel().listEffective();
        boolean roleAssigned = assignedRoles.stream()
                .map(RoleRepresentation::getName)
                .anyMatch(keycloakUser.getRole()::equals);
        if (!roleAssigned) {
            throw new RuntimeException("Failed to assign role to user.");
        }

    }
    public void assignRoleToUser(String userId, String roleName) {
        RealmResource realmResource = keycloak.realm("plateforme-pharmaceutique");
        UsersResource usersResource = realmResource.users();
        RolesResource rolesResource = realmResource.roles();

        RoleRepresentation role = rolesResource.get(roleName).toRepresentation();
        usersResource.get(userId).roles().realmLevel().add(Collections.singletonList(role));
    }



/*
        RealmResource realmResource = keycloak.realm(KeycloakConfig.realm);
        UsersResource usersResource = keycloak.realm(KeycloakConfig.realm).users();

        // Get the user by username
        List<UserRepresentation> userList = usersResource.search(userName);
        if (userList.isEmpty()) {
            throw new BadRequestException("User not found");
        }
        UserRepresentation kcUser = userList.get(0);

        System.out.println(userList);
        System.out.println(userList);
        System.out.println(userList);
        System.out.println(userList);

        // Get the list of available roles in the realm
        List<RoleRepresentation> availableRoles = realmResource.roles().list();

        // Find the role with the specified name
        RoleRepresentation targetRole = null;
        for (RoleRepresentation role : availableRoles) {
            if (role.getName().equals(keycloakUser.getRole())) {
                targetRole = role;
                break;
            }
        }

        if (targetRole == null) {
            throw new BadRequestException("Role not found");
        }

        System.out.println(targetRole);
        System.out.println(targetRole);
        System.out.println(targetRole);
        System.out.println(targetRole);

        // Assign the role to the user
        List<String> realmRoles = kcUser.getRealmRoles();
        if (realmRoles == null) {
             realmRoles = new ArrayList<>();
        }
        if (!realmRoles.contains(targetRole.getName())) {
            realmRoles.add(targetRole.getName());
        }
        kcUser.setRealmRoles(realmRoles);

        // Update the user in Keycloak
        UserResource userResource = usersResource.get(kcUser.getId());
        if (userResource == null) {
            throw new BadRequestException("User not found");
        }
        // Assign the role to the user
        realmRoles = kcUser.getRealmRoles();
        if (realmRoles == null) {
            realmRoles = new ArrayList<>();
        }
        if (!realmRoles.contains(targetRole.getName())) {
            realmRoles.add(targetRole.getName());
        }
        kcUser.setRealmRoles(realmRoles);

// Update the user in Keycloak
        userResource = usersResource.get(kcUser.getId());
        if (userResource == null) {
            throw new BadRequestException("User not found");
        }
        userResource.update(kcUser);

// Verify that the role was added to the user
        List<String> updatedRealmRoles = userResource.toRepresentation().getRealmRoles();
        if (updatedRealmRoles == null || !updatedRealmRoles.contains(targetRole.getName())) {
            logger.error("Failed to assign role {} to user {}", targetRole.getName(), userName);
            throw new RuntimeException("Failed to assign role to user");
        }

// Log the updated user and role
        logger.info("Assigned role {} to user {}", targetRole.getName(), userName);

    }

*/

        public void sendVerificationLink(String userId){
        UsersResource usersResource = getInstance();
        usersResource.get(userId)
                .sendVerifyEmail();
    }
    public void sendResetPassword(String userId){
        UsersResource usersResource = getInstance();

        usersResource.get(userId)
                .executeActionsEmail(Arrays.asList("UPDATE_PASSWORD"));
    }




}
