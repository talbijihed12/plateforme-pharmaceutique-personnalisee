package com.project.ppprapport.usermicroservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@FeignClient(name="Authentification", url = "http://authentification:8093")

public interface UserDto {
    @GetMapping("/patient/check")
     boolean isPatient(@RequestParam("patientName") String patientName);
    @GetMapping("/pharmacien/admin/findAllPharmaciens")
    List<KeycloakUser> getPharmacyUsers();

}
