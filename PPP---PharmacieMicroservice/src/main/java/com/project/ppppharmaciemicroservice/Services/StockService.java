package com.project.ppppharmaciemicroservice.Services;

import com.project.ppppharmaciemicroservice.DTO.StockDto;
import com.project.ppppharmaciemicroservice.DTO.StockDtoCreation;
import com.project.ppppharmaciemicroservice.Entity.Laboratoire;
import com.project.ppppharmaciemicroservice.Entity.Stock;
import com.project.ppppharmaciemicroservice.Exceptions.StockNotFoundException;
import com.project.ppppharmaciemicroservice.Repositories.LaboratoireRepo;
import com.project.ppppharmaciemicroservice.Repositories.StockRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.spi.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class StockService implements IStockService {
    private StockRepo stockRepo;
    private final LaboratoireService laboratoireService;
    private final LaboratoireRepo laboratoireRepo;

    @Transactional
    public Stock addStock(StockDtoCreation stockDtoCreation, String createdBy) {
        StockDto stockDto = stockDtoCreation.getStockDto();
        String laboratoireName = stockDtoCreation.getLaboratoireName();
        Laboratoire laboratoire = laboratoireRepo.findByNom(laboratoireName);
        if (laboratoire == null) {
            throw new IllegalArgumentException("Laboratoire not found with name: " + laboratoireName);
        }

        Stock stock = new Stock();
        stock.setId(stockDto.getId());
        stock.setQuantiteDisponible(stockDto.getQuantiteDisponible());
        stock.setUnite(stockDto.getUnite());
        stock.setDateArrivage(new Date());
        stock.setLaboratoireNom(laboratoireName);
        stock.setDateExpiration(stockDto.getDateExpiration());
        stock.setCreatedBy(createdBy);
        stock.setLaboratoire(laboratoire);
            stockRepo.save(stock);
            return stock;

    }


    private Laboratoire fetchLaboratoireDetailsByNom(String nom) {
            Laboratoire laboratories =  laboratoireService.findAllLaboByNom(nom);
        return laboratories;
    }



    @Override
    public List<Stock> findAllStocks() {
        return stockRepo.findAll();
    }

    @Override
    public Stock findStockById(Long id) {
        return stockRepo.findById(id).orElse(null);
    }

    @Override
    public List<Stock> findByLabo(Long laboratoireId) {

        return stockRepo.findStocksByLaboratoireId(laboratoireId);
    }

    @Override
    public void update(StockDto stockDto, String username, Long id) {
        Stock existingStock = stockRepo.findById(id)
                .orElseThrow(() -> new StockNotFoundException(id));
        if (!existingStock.getCreatedBy().equals(username)) {
            throw new UnauthorizedException("You are not authorized to update this Stock.");
        }
        existingStock.setQuantiteDisponible(stockDto.getQuantiteDisponible());
        existingStock.setUnite(stockDto.getUnite());
        existingStock.setDateArrivage(stockDto.getDateArrivage());
        existingStock.setDateExpiration(stockDto.getDateExpiration());


        stockRepo.save(existingStock);
    }
   /* @Scheduled(cron = "0 0 0 * * *")
    public void deleteExpiredStocks() {
        List<Stock> expiredStocks = stockRepo.findByDateExpirationBefore(new Date());
        stockRepo.deleteAll(expiredStocks);
    }*/
    @Override
    public ResponseEntity<List<Stock>> restock() {
        try {
            List<Stock> stockList = stockRepo.findAll().stream()
                    .filter(stock -> stock.getQuantiteDisponible() < 100)
                    .collect(Collectors.toList());
            stockList.forEach(stock -> {
                stock.setQuantiteDisponible(stock.getQuantiteDisponible() + 100);
                stockRepo.save(stock);
            });
            log.info(" restocked");
            return ResponseEntity.status(HttpStatus.OK).body(stockList);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }
    public List<Stock> findSoonToExpireStock(int thresholdDays) {
        LocalDate thresholdDate = LocalDate.now().plusDays(thresholdDays);
        return stockRepo.findByDateExpirationBefore(thresholdDate);
    }
    @Scheduled(cron = "0 0 0 * * MON") 
    public void deleteExpiredStocks() {
        LocalDate today = LocalDate.now();
        stockRepo.deleteByDateExpirationBefore(today);
    }

}
