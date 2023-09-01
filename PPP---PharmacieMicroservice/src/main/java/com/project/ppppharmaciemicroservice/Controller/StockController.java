package com.project.ppppharmaciemicroservice.Controller;

import com.project.ppppharmaciemicroservice.DTO.StockDto;
import com.project.ppppharmaciemicroservice.DTO.StockDtoCreation;
import com.project.ppppharmaciemicroservice.Entity.Stock;
import com.project.ppppharmaciemicroservice.Exceptions.StockNotFoundException;
import com.project.ppppharmaciemicroservice.Repositories.StockRepo;
import com.project.ppppharmaciemicroservice.Services.StockService;
import lombok.AllArgsConstructor;
import org.jboss.resteasy.spi.UnauthorizedException;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/stock")
@AllArgsConstructor
public class StockController {
    private final StockService stockService;
    @Autowired
    StockRepo stockRepo;
    @PostMapping("/add")
    public ResponseEntity createStock(@RequestBody StockDtoCreation stockDto, KeycloakAuthenticationToken auth) {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        String username = context.getToken().getPreferredUsername();
        stockService.addStock(stockDto,username);
        return new ResponseEntity(HttpStatus.CREATED);

    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateStock(@PathVariable Long id, @RequestBody StockDto stockDto, KeycloakAuthenticationToken auth) {
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) auth.getPrincipal();
        KeycloakSecurityContext context = principal.getKeycloakSecurityContext();
        String username = context.getToken().getPreferredUsername();
        try {
            stockService.update(stockDto, username,id);
            return ResponseEntity.ok("Stock has been updated.");
        } catch (StockNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
    @GetMapping
    public ResponseEntity<List<Stock>> getAllStocks() {
        return status(HttpStatus.OK).body(stockService.findAllStocks());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStock(@PathVariable Long id) {
        return status(HttpStatus.OK).body(stockService.findStockById(id));
    }
    @GetMapping("/getStockByLabo/{laboratoireId}")
    public ResponseEntity<List<Stock>> getStockByLabo(@PathVariable Long laboratoireId) {

        List<Stock> stocks = stockService.findByLabo(laboratoireId);

        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }
    @PutMapping("/restocklist")
    public ResponseEntity<List<Stock>> restock() {
        return stockService.restock();
    }
}
