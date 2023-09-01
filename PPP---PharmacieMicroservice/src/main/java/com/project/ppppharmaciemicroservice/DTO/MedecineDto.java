package com.project.ppppharmaciemicroservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedecineDto {
    private Date dateArrivage;
    private String nom;
    private int quantite;
    private String unite;
    private LocalDate dateExpiration;



}
