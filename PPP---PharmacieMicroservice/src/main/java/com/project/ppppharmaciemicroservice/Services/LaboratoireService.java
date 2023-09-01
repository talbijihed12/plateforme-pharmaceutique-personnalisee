package com.project.ppppharmaciemicroservice.Services;

import com.project.ppppharmaciemicroservice.Entity.Laboratoire;
import com.project.ppppharmaciemicroservice.Entity.SearchLabo;
import com.project.ppppharmaciemicroservice.Repositories.LaboratoireRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class LaboratoireService implements ILaboratoireService {
    @Autowired
    private LaboratoireRepo laboratoireRepo;
    @Override
    public List<Laboratoire> findAllLabo() {
        return laboratoireRepo.findAll();
    }
    @Override
    public Laboratoire findAllLaboByNom(String nom) {
        return laboratoireRepo.findByNom(nom);
    }
    @Override

    public ResponseEntity<Laboratoire> getLaboById(Long id) {
        Laboratoire getLabo = laboratoireRepo.findById(id).get();
        return new ResponseEntity<>(getLabo,HttpStatus.OK);
    }
    @Override

    public ResponseEntity<List<Laboratoire>> searchLabo(SearchLabo searchLabo) {

        try {
            List productDetails = laboratoireRepo.findAll()
                    .stream()
                    .filter(showModel -> Objects.equals(searchLabo.getNom(), showModel.getNom()) ||
                            Objects.equals(searchLabo.getAdresse(), showModel.getAdresse()) ||
                            Objects.equals(searchLabo.getTelephone(), showModel.getTelephone()) ||
                            Objects.equals(searchLabo.getEmail(), showModel.getEmail())
                    )
                    .limit(5).collect(Collectors.toList());


            return ResponseEntity.status(HttpStatus.OK).body(productDetails);
        }
        catch (Exception exception){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
