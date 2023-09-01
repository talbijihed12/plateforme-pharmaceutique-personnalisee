package com.project.ppprapport.Services;

import com.project.ppprapport.DTO.DossierDto;
import com.project.ppprapport.DTO.SearchDossier;
import com.project.ppprapport.Entity.Dossier;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IDossierService {
    Dossier createDossier(Dossier dossier, String createdBy);

    Dossier getDossierForPatient(String patientName);

    List<Dossier> findAllDossier();

    Dossier findDossierById(Long id);

    ResponseEntity<List<Dossier>> searchDossier(SearchDossier searchDossier);

    void update(DossierDto dossierDto, String username, Long id);

    ResponseEntity<Dossier> deleteDossier(Long id);
}
