package com.project.ppprendezvous.Entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ReservationMedicament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private String nameMedicament;
    private Boolean approved=false;
    private Boolean expired = false;
    String username;

    @ElementCollection
    List<Long> MedecinIdList;

}
