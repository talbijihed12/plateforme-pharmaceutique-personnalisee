package com.project.ppppharmaciemicroservice.Controller;

import com.project.ppppharmaciemicroservice.Entity.*;
import com.project.ppppharmaciemicroservice.Repositories.LaboratoireRepo;
import com.project.ppppharmaciemicroservice.Repositories.MedecinRepo;
import com.project.ppppharmaciemicroservice.Services.LaboratoireService;
import com.project.ppppharmaciemicroservice.Services.MedecinService;
import com.project.ppppharmaciemicroservice.usermicroservice.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Labo")
@AllArgsConstructor
public class LaboController {

    private final LaboratoireService laboratoireService;
    @Autowired
    LaboratoireRepo laboratoireRepo;
    @GetMapping("/all")
    public List<Laboratoire> getAllMedicaments() {
        return laboratoireService.findAllLabo();
    }
    @GetMapping("/byname/{nom}")
    public Laboratoire getlabo(@PathVariable String nom) {
        return laboratoireService.findAllLaboByNom(nom);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Laboratoire> getMedicamentsById(@PathVariable Long id) {
        return laboratoireService.getLaboById(id);
    }
    @PostMapping("/search")
    public ResponseEntity<List<Laboratoire>> searchMedicine(@RequestBody SearchLabo searchProduct) {
        return laboratoireService.searchLabo(searchProduct);
    }

}
