package com.project.ppppharmaciemicroservice.Repositories;

import com.project.ppppharmaciemicroservice.Entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
    public interface StockRepo  extends JpaRepository<Stock,Long> {
    List<Stock> findStocksByLaboratoireId(Long laboratoireId);

    List<Stock> findByDateExpirationBefore(LocalDate thresholdDate);

    void deleteByDateExpirationBefore(LocalDate today);
}
