package com.project.pppmagasinmicroservice.Utilis.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentTO {
    private Long idComment;
    private Long postId;
    private String text;


}
