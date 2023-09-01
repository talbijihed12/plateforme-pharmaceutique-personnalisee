package com.project.pppmagasinmicroservice.Utilis.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse extends AuditableResponse<String> {
    private Long id;
    private String postName;
    private String description;
    private String hashTag;
    private String username;
    private String topic;
    private Integer commentCount;
    private String duration;






}
