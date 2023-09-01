package com.project.ppppharmaciemicroservice.Repositories;

import com.project.ppppharmaciemicroservice.Entity.Medicaments;
import com.project.ppppharmaciemicroservice.Entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedecinRepo extends JpaRepository<Medicaments,Long> {


    List<Medicaments> findByNom(String medicineName);


}
