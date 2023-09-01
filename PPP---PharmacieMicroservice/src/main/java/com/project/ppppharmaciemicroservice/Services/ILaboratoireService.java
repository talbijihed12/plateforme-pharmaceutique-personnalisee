package com.project.ppppharmaciemicroservice.Services;

import com.project.ppppharmaciemicroservice.Entity.Laboratoire;
import com.project.ppppharmaciemicroservice.Entity.SearchLabo;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ILaboratoireService {

    List<Laboratoire> findAllLabo();

    Laboratoire findAllLaboByNom(String nom);

    ResponseEntity<Laboratoire> getLaboById(Long id);

    ResponseEntity<List<Laboratoire>> searchLabo(SearchLabo searchLabo);
}
