package com.project.ppprapport.Services;

import com.project.ppprapport.Config.KeycloakConfig;
import com.project.ppprapport.DTO.DossierDto;
import com.project.ppprapport.DTO.SearchDossier;
import com.project.ppprapport.Entity.Dossier;
import com.project.ppprapport.Exceptions.DossierNotFoundException;
import com.project.ppprapport.Repositories.DossierRepo;
import com.project.ppprapport.usermicroservice.UserDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class DossierService implements IDossierService {
    @Autowired
    private UserDto userDto;
    private final Keycloak keycloak;

    private final DossierRepo dossierRepo;

    public Dossier createDossier(Dossier dossier, String createdBy) {
        String patientName = dossier.getPatient();
        UsersResource usersResource = keycloak.realm(KeycloakConfig.realm).users();

        boolean hasPatientRole = hasPatientRole(usersResource, patientName);

        if (hasPatientRole) {
            dossier.setCreatedBy(createdBy);
            dossier.setEtat(dossier.getEtat());
            dossier.setPatient(patientName);
            return dossierRepo.save(dossier);
        } else {
            throw new IllegalArgumentException("Patient name does not exist or is not a patient.");
        }
    }


    private boolean hasPatientRole(UsersResource usersResource, String username) {
        List<UserRepresentation> userList = usersResource.search(username);

        if (!userList.isEmpty()) {
            UserRepresentation user = userList.get(0);
            List<RoleRepresentation> userRoles = usersResource.get(user.getId()).roles().realmLevel().listAll();
            return userRoles.stream().anyMatch(userRole -> userRole.getName().equals("Patient"));
        }

        return false;
    }


    @Override
    public Dossier getDossierForPatient(String patientName) {
        return dossierRepo.findByPatient(patientName);
    }
    @Override
    public List<Dossier> findAllDossier() {
        return dossierRepo.findAll();
    }
    @Override
    public Dossier findDossierById(Long id) {
        return dossierRepo.findById(id).orElse(null);
    }
    @Override

    public ResponseEntity<List<Dossier>> searchDossier(SearchDossier searchDossier) {

        try {
            List productDetails = dossierRepo.findAll()
                    .stream()
                    .filter(showModel -> Objects.equals(searchDossier.getEtat(), showModel.getEtat()) ||
                            Objects.equals(searchDossier.getCreatedBy(), showModel.getCreatedBy()) ||
                            Objects.equals(searchDossier.getPatient(), showModel.getPatient())

                    )
                    .limit(5).collect(Collectors.toList());


            return ResponseEntity.status(HttpStatus.OK).body(productDetails);
        }
        catch (Exception exception){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void update(DossierDto dossierDto, String username, Long id) {
        Dossier dossier = dossierRepo.findById(id)
                .orElseThrow(() -> new DossierNotFoundException(id));
        dossier.setEtat(dossierDto.getEtat());
        dossierRepo.save(dossier);
}


    @Override

    public ResponseEntity<Dossier> deleteDossier(Long id) {
        try {
            List dossiers = dossierRepo.findAll().stream()
                    .filter(item-> item.getId().equals(id))
                    .collect(Collectors.toList());
            if(!dossiers.isEmpty()) {
                dossierRepo.deleteById(id);
                return new ResponseEntity<Dossier>(HttpStatus.OK);

            } else {
                return new ResponseEntity<Dossier>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<Dossier>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }


}
