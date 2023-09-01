package com.project.pppmagasinmicroservice.Utilis.Exception;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(Long idComment) {
        super("Post with pubId " + idComment + " not found.");

    }
}
