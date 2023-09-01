package com.project.ppprapport.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Dossier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String patient;
    @Enumerated(EnumType.STRING)
    private Etat etat;
    private String createdBy;

    @OneToMany(cascade= {CascadeType.REMOVE}, mappedBy = "dossier")
    Set<Ordonnance> ordonnances=new HashSet<>();

    @OneToMany(cascade= {CascadeType.REMOVE}, mappedBy = "dossier")
    Set<Rapport> rapports=new HashSet<>();
}
