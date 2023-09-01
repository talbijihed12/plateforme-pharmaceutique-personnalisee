package com.project.ppppharmaciemicroservice.Services;

import com.project.ppppharmaciemicroservice.DTO.StockDto;
import com.project.ppppharmaciemicroservice.Entity.Stock;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IStockService {




    List<Stock> findAllStocks();

    Stock findStockById(Long id);

    List<Stock> findByLabo(Long id);

    void update(StockDto stockDto, String username, Long id);

    ResponseEntity<List<Stock>> restock();
}
