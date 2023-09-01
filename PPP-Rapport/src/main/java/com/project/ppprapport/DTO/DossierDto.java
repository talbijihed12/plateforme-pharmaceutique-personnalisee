package com.project.ppprapport.DTO;

import com.project.ppprapport.Entity.Etat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DossierDto {
    @Enumerated(EnumType.STRING)
    private Etat etat;
}
