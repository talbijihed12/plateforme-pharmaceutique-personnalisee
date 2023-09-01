package com.project.pppmagasinmicroservice.Utilis.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest  {
    private Long postId;
    private String topic;
    private String postName;
    private String hashtag;
    private String description;


}
