package com.project.ppppharmaciemicroservice.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Table(name = "laboratoires")
public class Laboratoire implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "nom cannot be empty or null")
    private String nom;
    @NotBlank(message = "adresse cannot be empty or null")
    private String adresse;
    private String telephone;
    private String email;
    @JsonIgnore
    @OneToMany(cascade= {CascadeType.REMOVE}, mappedBy = "laboratoire")
    Set<Medicaments> medicaments=new HashSet<>();
    @JsonIgnore
    @OneToMany(cascade= {CascadeType.REMOVE}, mappedBy = "laboratoire")
    Set<Stock> stocks=new HashSet<>();
}
