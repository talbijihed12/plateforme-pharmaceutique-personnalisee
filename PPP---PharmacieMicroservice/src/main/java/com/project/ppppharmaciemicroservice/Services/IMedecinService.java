package com.project.ppppharmaciemicroservice.Services;

import com.project.ppppharmaciemicroservice.DTO.MedecineDto;
import com.project.ppppharmaciemicroservice.DTO.StockDto;
import com.project.ppppharmaciemicroservice.DTO.StockDtoCreation;
import com.project.ppppharmaciemicroservice.Entity.Medicaments;
import com.project.ppppharmaciemicroservice.Entity.SearchMedecin;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

public interface IMedecinService {



    void compareAndAddMedicine(MedecineDto medecineDto, String username) throws IOException;

    ResponseEntity<List<Medicaments>> findAllMedecine();


    ResponseEntity<Medicaments> getMedecineById(Long id);

    ResponseEntity<List<Medicaments>> searchMedicine(SearchMedecin searchMedecin);


    void update(MedecineDto medecineDto, String username, Long id);

    ResponseEntity<Medicaments> deleteMedicament(Long id);

    List<Long> retrieveMedecinIdsByNames(List<String> medecinNames);

    boolean isMedicineAvailable(String medicineName);

    boolean isNotExpired(String medicineName);
}
