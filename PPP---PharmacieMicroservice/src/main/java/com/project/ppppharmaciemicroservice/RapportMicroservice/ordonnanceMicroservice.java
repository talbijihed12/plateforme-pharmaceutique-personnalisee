package com.project.ppppharmaciemicroservice.RapportMicroservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="RapportMicroservice", url = "http://rapport:8095/Ordonnance")

public interface ordonnanceMicroservice {
    @GetMapping("/medecins/{nomMalade}")
    List<String> getDrugsForPatient(@PathVariable String nomMalade);
}
