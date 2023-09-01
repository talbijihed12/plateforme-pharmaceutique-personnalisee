package com.project.ppprapport.Services;

import com.project.ppprapport.DTO.MessageResponse;
import com.project.ppprapport.DTO.RapportDto;
import com.project.ppprapport.DTO.SearchRapport;
import com.project.ppprapport.Entity.Rapport;
import com.sun.media.sound.InvalidDataException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IRapportService {
    MessageResponse addRapport(List<MultipartFile> files, Rapport rapport, String createdBy);

    List<Rapport> findRapportByNomMalade(String nomMalade);

    List<Rapport> findAllRapports(String createdBy);

    Rapport findRapportById(Long id, String createdBy);

    ResponseEntity<List<Rapport>> searchRapports(SearchRapport searchRapport);

    void update(RapportDto rapportDto, String createdBy, Long id) throws InvalidDataException;

    ResponseEntity<Rapport> deleteRapport(Long id);
}
