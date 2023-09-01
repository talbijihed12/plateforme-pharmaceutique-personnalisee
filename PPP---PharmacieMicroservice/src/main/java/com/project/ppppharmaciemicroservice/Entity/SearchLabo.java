package com.project.ppppharmaciemicroservice.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Data
@Setter
@Getter
public class SearchLabo {

    private String nom;
    private String adresse;
    private String telephone;
    private String email;
}
