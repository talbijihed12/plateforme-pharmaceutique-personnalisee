package com.project.ppprapport.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Data
@Setter
@Getter
public class SearchOrdonnance {
    private String nomMalade;
    private String codeCnam;
    private int refAdministrative;
    private String regimeSocial;
    private String medicament;
    private Date date;
    private String description;
}
