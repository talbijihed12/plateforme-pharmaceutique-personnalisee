package com.project.ppppharmaciemicroservice.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Stock implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantiteDisponible;
    private String unite;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateArrivage;
    private LocalDate dateExpiration;
    private String createdBy;
    private String laboratoireNom;
    @JsonIgnore
    @OneToMany(cascade= {CascadeType.REMOVE}, mappedBy = "stock")
    Set<Medicaments> medicaments=new HashSet<>();
    @ManyToOne
    Laboratoire laboratoire;
}
