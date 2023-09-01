package com.project.ppppharmaciemicroservice.Exceptions;

public class MedecineNotFoundException extends RuntimeException {

    public MedecineNotFoundException(Long id) {
        super("Medecine with pubId " + id + " not found.");

    }
}