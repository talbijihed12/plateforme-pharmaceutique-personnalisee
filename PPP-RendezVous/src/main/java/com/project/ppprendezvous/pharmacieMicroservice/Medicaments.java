package com.project.ppprendezvous.pharmacieMicroservice;


import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Medicaments implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "nom cannot be empty or null")
    private String nom;
    private String dosage;
    @NotBlank(message = "forme cannot be empty or null")
    private String forme;
    @NotBlank(message = "presentation cannot be empty or null")
    private String presentation;
    @NotBlank(message = "dci cannot be empty or null")
    private String dci;
    @NotBlank(message = "classe cannot be empty or null")
    private String classe;
    @NotBlank(message = "sousClasse cannot be empty or null")
    private String sousClasse;
    @NotBlank(message = "amm cannot be empty or null")
    private String amm;
    private Date dateAmm;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateArrivage;
    @NotBlank(message = " conditionnement Primaire cannot be empty or null")
    private String conditionnementPrimaire;
    @NotBlank(message = "specifique Conditionnement Primaire cannot be empty or null")
    private String sConditionnementPrimaire;
    private String nomLaboratoire;



}
