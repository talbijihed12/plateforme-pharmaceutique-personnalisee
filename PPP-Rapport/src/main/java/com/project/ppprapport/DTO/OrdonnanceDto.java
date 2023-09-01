package com.project.ppprapport.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdonnanceDto {
    private String nomMalade;
    private String codeCnam;
    private int refAdministrative;
    private String regimeSocial;
    private String medicament;
    private String Etiquette;
    private Date date;
    private String description;
}
