package com.project.ppprapport.Exceptions;

public class OrdonnanceNotFoundException extends RuntimeException {

    public OrdonnanceNotFoundException(Long id) {
        super("Ordonnance with pubId " + id + " not found.");

    }
}