package com.project.ppprapport.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RapportDto {
    private String analyses;
    private String conseil;
    private Date date;
    private String description;
}
