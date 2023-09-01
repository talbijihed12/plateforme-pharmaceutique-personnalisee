package com.project.ppprapport.Repositories;

import com.project.ppprapport.Entity.Ordonnance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdonnanceRepo extends JpaRepository<Ordonnance,Long> {
    List<Ordonnance> findByNomMalade(String nomMalade);
}
