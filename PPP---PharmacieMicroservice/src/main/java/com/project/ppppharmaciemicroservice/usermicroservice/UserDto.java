package com.project.ppppharmaciemicroservice.usermicroservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
@FeignClient(name="Authentification", url = "http://authentification:8093/pharmacien")

public interface UserDto {
    @GetMapping("/admin/findAllPharmaciens")
     List<KeycloakUser> getPharmacyUsers();

}
