package com.project.ppprapport.pharmacieMicroservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@FeignClient(name="PharmacieMicroservice", url = "http://pharmacy:8094")

public interface MedecinServices {
    @GetMapping("/medecin/retrieve")
    List<String> retrieveMedicines();
    @GetMapping("/getDose")
    String getDose(@RequestParam String medicineName) ;

}
