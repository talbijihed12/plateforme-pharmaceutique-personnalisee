package com.project.ppppharmaciemicroservice.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@Setter
@Getter
public class SearchMedecin {
    private Long id;
    private String nom;
    private String dosage;
    private String forme;
    private String presentation;
    private String dci;
    private String classe;
    private String sousClasse;
    private String amm;
    private Date dateAmm;
    private Date dateArrivage;
    private String conditionnementPrimaire;
    private String sConditionnementPrimaire;
    private String nomLaboratoire;
    private Tableau tableau;
    private int dureeConservation;
    private String indication;
    private GPB gpb;
    private VEIC veic;
}
