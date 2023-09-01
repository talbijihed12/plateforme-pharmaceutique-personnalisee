package com.project.ppppharmaciemicroservice.DTO;

import com.project.ppppharmaciemicroservice.Entity.Laboratoire;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StockDto {
    private Long id;
    private int quantiteDisponible;
    private String unite;
    private Date dateArrivage;
    private LocalDate dateExpiration;
    private String laboratoireNom;
    private Laboratoire laboratoire;


}
