package com.project.ppppharmaciemicroservice.Exceptions;

public class StockNotFoundException extends RuntimeException {

    public StockNotFoundException(Long id) {
        super("Stock with pubId " + id + " not found.");

    }
}