package com.project.ppppharmaciemicroservice.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StockDtoCreation {
    private StockDto stockDto;
    private String laboratoireName;
}
