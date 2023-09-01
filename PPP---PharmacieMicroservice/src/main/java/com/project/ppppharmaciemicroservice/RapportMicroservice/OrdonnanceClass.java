package com.project.ppppharmaciemicroservice.RapportMicroservice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdonnanceClass {
    private Long id;
    private String nomMalade;
    private String codeCnam;
    private String createdBy;
    private int refAdministrative;
    private String regimeSocial;
    private String medicament;
    private String Etiquette;
    private Date date;
    private String description;
}
