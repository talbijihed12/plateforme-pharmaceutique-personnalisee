package com.project.ppprapport.DTO;

import com.project.ppprapport.Entity.Etat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Setter
@Getter
public class SearchDossier {
    private String patient;
    private Etat etat;
    private String createdBy;
}
