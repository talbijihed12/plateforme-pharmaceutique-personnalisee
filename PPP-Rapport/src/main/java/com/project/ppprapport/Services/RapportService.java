package com.project.ppprapport.Services;

import com.project.ppprapport.DTO.MessageResponse;
import com.project.ppprapport.DTO.RapportDto;
import com.project.ppprapport.DTO.SearchRapport;
import com.project.ppprapport.Entity.Dossier;
import com.project.ppprapport.Entity.Rapport;
import com.project.ppprapport.Exceptions.OrdonnanceNotFoundException;
import com.project.ppprapport.Repositories.DossierRepo;
import com.project.ppprapport.Repositories.RapportRepo;
import com.sun.media.sound.InvalidDataException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.NotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class RapportService implements IRapportService {
    private final RapportRepo rapportRepo;
    private final DossierRepo dossierRepo;
    private final Path root = Paths.get("uploads");

    @Override
    public MessageResponse addRapport(List<MultipartFile> files, Rapport rapport, String createdBy){
        Dossier dossier = dossierRepo.findByPatient(rapport.getNomMalade());
        if (dossier == null) {
            throw new NotFoundException("Dossier not found for nomMalade: " + rapport.getNomMalade());
        }
        try {
            for (MultipartFile file: files) {
                Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
                rapport.setDescription(rapport.getDescription());
                rapport.setCreatedBy(createdBy);
                rapport.setImagerieMedicale(file.getOriginalFilename());
                rapport.setConseil(rapport.getConseil());
                rapport.setAnalyses(rapport.getAnalyses());
                rapport.setDate(new Date());
                rapport.setDossier(dossier);
                rapportRepo.save(rapport);
            }
        }  catch (IOException e) {
            e.printStackTrace();
            return new MessageResponse(false, "Attention", "Operation not effectuated");
        }

        return  new MessageResponse(true, "Success", "Operation  effectuated");

    }
@Override
    public List<Rapport> findRapportByNomMalade(String nomMalade) {
        List<Rapport> rapports = rapportRepo.findByNomMalade(nomMalade);
        return rapports;
    }

    @Override
    public List<Rapport> findAllRapports(String createdBy) {
        return rapportRepo.findAll();
    }
    @Override
    public Rapport findRapportById(Long id, String createdBy) {
        return rapportRepo.findById(id).orElse(null);
    }
    @Override

    public ResponseEntity<List<Rapport>> searchRapports(SearchRapport searchRapport) {

        try {
            List productDetails = rapportRepo.findAll()
                    .stream()
                    .filter(showModel -> Objects.equals(searchRapport.getNomMalade(), showModel.getNomMalade()) ||
                            Objects.equals(searchRapport.getCreatedBy(), showModel.getCreatedBy()) ||
                            Objects.equals(searchRapport.getDate(), showModel.getDate())


                    )
                    .limit(5).collect(Collectors.toList());


            return ResponseEntity.status(HttpStatus.OK).body(productDetails);
        }
        catch (Exception exception){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void update(RapportDto rapportDto, String createdBy, Long id) throws InvalidDataException {
        Rapport rapport = rapportRepo.findById(id)
                .orElseThrow(() -> new OrdonnanceNotFoundException(id));
        Dossier dossier = dossierRepo.findByPatient(rapport.getNomMalade());
        if (dossier == null) {
            throw new NotFoundException("Dossier not found for nomMalade: " + rapport.getNomMalade());
        }
        rapport.setAnalyses(rapportDto.getAnalyses());
        rapport.setConseil(rapportDto.getConseil());
        rapport.setDescription(rapportDto.getDescription());
        rapport.setDate(new Date());
        dossierRepo.save(dossier);
    }


    @Override
    public ResponseEntity<Rapport> deleteRapport(Long id) {
        try {
            List rapports = rapportRepo.findAll().stream()
                    .filter(item-> item.getId().equals(id))
                    .collect(Collectors.toList());
            if(!rapports.isEmpty()) {
                rapportRepo.deleteById(id);
                return new ResponseEntity<Rapport>(HttpStatus.OK);

            } else {
                return new ResponseEntity<Rapport>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<Rapport>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }


}
