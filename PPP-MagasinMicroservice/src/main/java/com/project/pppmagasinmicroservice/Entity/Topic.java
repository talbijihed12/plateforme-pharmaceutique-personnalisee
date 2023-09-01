package com.project.pppmagasinmicroservice.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.Nullable;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Topic extends Auditable<String>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "community name is required")
    private String name;
    @NotBlank(message = "description is required")
    private String description;
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "topic")
    @JsonIgnore
    private List<Post> posts;
    private String createdBy;
    private String lastModifiedBy;



}
