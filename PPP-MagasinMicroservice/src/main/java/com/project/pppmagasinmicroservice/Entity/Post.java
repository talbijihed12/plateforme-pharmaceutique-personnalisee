package com.project.pppmagasinmicroservice.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.Nullable;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)

public class Post extends Auditable<String>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pubId;
    @NotBlank(message = "Post Name cannot be empty or null")
    private String postName;
    @Nullable
    private String hashTag;
    @Nullable

    private String description;
    @ManyToOne

    private Topic topic;
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "post",cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Comment> comments;


    private String createdBy;
    private String lastModifiedBy;


}
