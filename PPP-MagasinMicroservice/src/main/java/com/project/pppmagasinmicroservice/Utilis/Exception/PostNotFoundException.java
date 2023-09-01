package com.project.pppmagasinmicroservice.Utilis.Exception;

public class PostNotFoundException  extends RuntimeException {

    public PostNotFoundException(Long postId) {
        super("Post with pubId " + postId + " not found.");

    }
    }

