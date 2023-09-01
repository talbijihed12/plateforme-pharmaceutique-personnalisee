package com.project.ppprapport.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Ordonnance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @ManyToOne
    @JsonIgnore
    Dossier dossier;
}
