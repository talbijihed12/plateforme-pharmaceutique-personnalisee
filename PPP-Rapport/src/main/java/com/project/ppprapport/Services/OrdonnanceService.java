package com.project.ppprapport.Services;

import com.project.ppprapport.DTO.OrdonnanceDto;
import com.project.ppprapport.DTO.SearchOrdonnance;
import com.project.ppprapport.Entity.Dossier;
import com.project.ppprapport.Entity.Ordonnance;
import com.project.ppprapport.Exceptions.OrdonnanceNotFoundException;
import com.project.ppprapport.Repositories.DossierRepo;
import com.project.ppprapport.Repositories.OrdonnanceRepo;
import com.project.ppprapport.pharmacieMicroservice.MedecinServices;
import com.sun.media.sound.InvalidDataException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class OrdonnanceService implements IOrdonnanceService{
    private final OrdonnanceRepo ordonnanceRepo;
    private final DossierRepo dossierRepo;
    @Autowired
    private MedecinServices medecinServices;
    @Override
    public void createOrdonnance(Ordonnance ordonnance, String createdBy) throws InvalidDataException {
        Dossier dossier = dossierRepo.findByPatient(ordonnance.getNomMalade());
        if (dossier == null) {
            throw new NotFoundException("Dossier not found for nomMalade: " + ordonnance.getNomMalade());
        }
        List<String> medicinesFromMicroservice = medecinServices.retrieveMedicines();
        String[] medications = ordonnance.getMedicament().split(",");
        for (String medication : medications) {
            String trimmedMedication = medication.trim();
            if (!medicinesFromMicroservice.contains(trimmedMedication)) {
                throw new InvalidDataException("Invalid medicament: " + trimmedMedication);
            }
        }
        ordonnance.setDossier(dossier);
        ordonnance.setMedicament(ordonnance.getMedicament());
        ordonnance.setNomMalade(ordonnance.getNomMalade());
        ordonnance.setEtiquette(ordonnance.getEtiquette());
        ordonnance.setCreatedBy(createdBy);
        ordonnance.setDate(new Date());
        ordonnance.setDescription(ordonnance.getDescription());
        ordonnance.setCodeCnam(ordonnance.getCodeCnam());
        ordonnance.setRefAdministrative(ordonnance.getRefAdministrative());
        ordonnance.setRegimeSocial(ordonnance.getRegimeSocial());
        ordonnanceRepo.save(ordonnance);
    }
    public List<Ordonnance> findOrdonnanceByNomMalade(String nomMalade) {
        List<Ordonnance> ordonnanceOptional = ordonnanceRepo.findByNomMalade(nomMalade);
        return ordonnanceOptional;
    }

    @Override
    public List<Ordonnance> getOrdonnanceForPatient(String nomMalade) {
        return ordonnanceRepo.findByNomMalade(nomMalade);
    }

    @Override
    public List<String> getMedecinsForPatient(String nomMalade) {
        List<Ordonnance> ordonnances = ordonnanceRepo.findByNomMalade(nomMalade);
        List<String> medecins = new ArrayList<>();

        for (Ordonnance ordonnance : ordonnances) {
            String medecinsAttribute = ordonnance.getMedicament();
            String[] medecinArray = medecinsAttribute.split(",");
            for (String medecin : medecinArray) {
                medecins.add(medecin.trim());
            }
        }

        return medecins;
    }


    @Override
    public List<Ordonnance> findAllOrdonnance(String createdBy) {
        return ordonnanceRepo.findAll();
    }
    @Override
    public Ordonnance findOrdonnanceById(Long id, String createdBy) {
        return ordonnanceRepo.findById(id).orElse(null);
    }
    @Override

    public ResponseEntity<List<Ordonnance>> searchOrdonnance(SearchOrdonnance searchOrdonnance) {

        try {
            List productDetails = ordonnanceRepo.findAll()
                    .stream()
                    .filter(showModel -> Objects.equals(searchOrdonnance.getNomMalade(), showModel.getNomMalade()) ||
                            Objects.equals(searchOrdonnance.getCodeCnam(), showModel.getCodeCnam()) ||
                            Objects.equals(searchOrdonnance.getRefAdministrative(), showModel.getRefAdministrative())||
                            Objects.equals(searchOrdonnance.getRegimeSocial(), showModel.getRegimeSocial())||
                            Objects.equals(searchOrdonnance.getMedicament(), showModel.getMedicament())||
                            Objects.equals(searchOrdonnance.getDescription(), showModel.getDescription())

                    )
                    .limit(5).collect(Collectors.toList());


            return ResponseEntity.status(HttpStatus.OK).body(productDetails);
        }
        catch (Exception exception){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void update(OrdonnanceDto ordonnanceDto, String createdBy, Long id) throws InvalidDataException {
        Ordonnance ordonnance = ordonnanceRepo.findById(id)
                .orElseThrow(() -> new OrdonnanceNotFoundException(id));
        Dossier dossier = dossierRepo.findByPatient(ordonnance.getNomMalade());
        if (dossier == null) {
            throw new NotFoundException("Dossier not found for nomMalade: " + ordonnance.getNomMalade());
        }
        List<String> medicinesFromMicroservice = medecinServices.retrieveMedicines();
        String[] medications = ordonnanceDto.getMedicament().split(",");
        for (String medication : medications) {
            String trimmedMedication = medication.trim();
            if (!medicinesFromMicroservice.contains(trimmedMedication)) {
                throw new InvalidDataException("Invalid medicament: " + trimmedMedication);
            }
        }
        ordonnance.setNomMalade(ordonnanceDto.getNomMalade());
        ordonnance.setCodeCnam(ordonnanceDto.getCodeCnam());
        ordonnance.setRefAdministrative(ordonnanceDto.getRefAdministrative());
        ordonnance.setRegimeSocial(ordonnanceDto.getRegimeSocial());
        ordonnance.setMedicament(ordonnanceDto.getMedicament());
        ordonnance.setEtiquette(ordonnanceDto.getEtiquette());
        ordonnance.setDate(new Date());
        ordonnance.setDescription(ordonnanceDto.getDescription());
        dossierRepo.save(dossier);
    }


    @Override
    public ResponseEntity<Ordonnance> deleteOrdonnance(Long id) {
        try {
            List ordonnances = ordonnanceRepo.findAll().stream()
                    .filter(item-> item.getId().equals(id))
                    .collect(Collectors.toList());
            if(!ordonnances.isEmpty()) {
                ordonnanceRepo.deleteById(id);
                return new ResponseEntity<Ordonnance>(HttpStatus.OK);

            } else {
                return new ResponseEntity<Ordonnance>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<Ordonnance>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

}
