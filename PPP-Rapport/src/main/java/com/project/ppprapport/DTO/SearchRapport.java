package com.project.ppprapport.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Setter
@Getter
public class SearchRapport {
    private String nomMalade;
    private String createdBy;
    private Date date;
}
