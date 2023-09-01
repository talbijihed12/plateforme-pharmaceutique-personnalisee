package com.project.ppppharmaciemicroservice.Repositories;

import com.project.ppppharmaciemicroservice.Entity.Laboratoire;
import com.project.ppppharmaciemicroservice.Entity.Medicaments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LaboratoireRepo extends JpaRepository<Laboratoire,Long> {
    Laboratoire findByNom(String nom);
}
