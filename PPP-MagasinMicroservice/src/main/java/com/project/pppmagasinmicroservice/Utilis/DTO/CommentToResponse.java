package com.project.pppmagasinmicroservice.Utilis.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentToResponse extends AuditableResponse<String>  {
    private Long idComment;
    private Long postId;
    private String text;
    private String duration;



}
