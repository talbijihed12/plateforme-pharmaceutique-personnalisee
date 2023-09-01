package com.project.ppprapport.Repositories;

import com.project.ppprapport.Entity.Rapport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RapportRepo extends JpaRepository<Rapport,Long> {
    List<Rapport> findByNomMalade(String nomMalade);
}
