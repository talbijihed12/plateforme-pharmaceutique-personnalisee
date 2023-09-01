package com.project.ppprendezvous.pharmacieMicroservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="PharmacieMicroservice", url = "http://pharmacy:8094")

public interface MedecinServices {
    @GetMapping("/medecin/retrieve")
    List<String> retrieveMedicines();
    @GetMapping("/getDose")
    String getDose(@RequestParam String medicineName) ;
    @GetMapping("/medecin/{id}")
    ResponseEntity<Medicaments> getMedicamentsById(@PathVariable Long id);
    @GetMapping("/medecin/ids")
     ResponseEntity<List<Long>> retrieveMedecinIdsByNames(@RequestParam List<String> medecinNames) ;

}
