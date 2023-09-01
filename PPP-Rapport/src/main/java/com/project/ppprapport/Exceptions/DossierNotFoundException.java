package com.project.ppprapport.Exceptions;

public class DossierNotFoundException  extends RuntimeException {

    public DossierNotFoundException(Long id) {
        super("Medecine with pubId " + id + " not found.");

    }
}