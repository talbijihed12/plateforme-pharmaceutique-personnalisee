package com.project.ppprapport.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Table(name = "rapport")
public class Rapport implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomMalade;
    private String createdBy;
    private String imagerieMedicale;
    private String analyses;
    private String conseil;
    private Date date;
    private String description;
    @ManyToOne
    @JsonIgnore
    Dossier dossier;
}
