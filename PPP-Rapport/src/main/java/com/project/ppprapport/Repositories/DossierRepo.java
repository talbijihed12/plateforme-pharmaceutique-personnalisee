package com.project.ppprapport.Repositories;

import com.project.ppprapport.Entity.Dossier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DossierRepo extends JpaRepository<Dossier,Long> {
    Dossier findByPatient(String patientName);
}
