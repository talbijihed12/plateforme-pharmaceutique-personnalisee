package com.project.ppppharmaciemicroservice.Services;

import com.project.ppppharmaciemicroservice.DTO.MedecineDto;
import com.project.ppppharmaciemicroservice.Entity.*;
import com.project.ppppharmaciemicroservice.Exceptions.MedecineNotFoundException;
import com.project.ppppharmaciemicroservice.Repositories.LaboratoireRepo;
import com.project.ppppharmaciemicroservice.Repositories.MedecinRepo;
import com.project.ppppharmaciemicroservice.Repositories.StockRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
@Slf4j
public class MedecinService implements IMedecinService {

    private final MedecinRepo medecinRepo;
    private final LaboratoireRepo laboratoireRepo;
    private final StockService stockService;
    private final StockRepo stockRepo;
    private final Map<String, String> medicinesAndDoses = new HashMap<>();



    @Override
    public void compareAndAddMedicine(MedecineDto medecineDto, String username) throws IOException {
        ClassPathResource resource = new ClassPathResource("liste_amm_3.xlsx");
        try (InputStream inputStream = resource.getInputStream()) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                Cell cell = row.getCell(0);
                if (cell != null && CellType.STRING.equals(cell.getCellType())) {
                    String medicineName = cell.getStringCellValue();
                    if (medicineName.equalsIgnoreCase(medecineDto.getNom())) {
                        Medicaments medicine = new Medicaments();
                        medicine.setNom(medicineName);
                        Date today = new Date();
                        medicine.setDateArrivage(today);
                        medicine.setDosage(row.getCell(1).getStringCellValue());
                        medicine.setForme(row.getCell(2).getStringCellValue());
                        medicine.setPresentation(row.getCell(3).getStringCellValue());
                        medicine.setDci(row.getCell(4).getStringCellValue());
                        medicine.setClasse(row.getCell(5).getStringCellValue());
                        medicine.setSousClasse(row.getCell(6).getStringCellValue());
                        medicine.setNomLaboratoire(row.getCell(7).getStringCellValue());
                        medicine.setAmm(row.getCell(8).getStringCellValue());
                        medicine.setDateAmm(row.getCell(9).getDateCellValue());
                        medicine.setConditionnementPrimaire(row.getCell(10).getStringCellValue());
                        medicine.setSConditionnementPrimaire(row.getCell(11).getStringCellValue());
                        medicine.setTableau(Tableau.valueOf(row.getCell(12).getStringCellValue()));
                        Cell dureeConservationCell = row.getCell(13);
                        if (dureeConservationCell != null) {
                            if (dureeConservationCell.getCellType() == CellType.STRING) {
                                medicine.setDureeConservation(dureeConservationCell.getStringCellValue());
                            } else if (dureeConservationCell.getCellType() == CellType.NUMERIC) {
                                medicine.setDureeConservation(String.valueOf(dureeConservationCell.getNumericCellValue()));
                            } else {
                                throw new IllegalArgumentException("Error cell type.");
                            }
                        }
                            medicine.setIndication(row.getCell(14).getStringCellValue());
                        medicine.setGpb(GPB.valueOf(row.getCell(15).getStringCellValue()));
                        medicine.setVeic(VEIC.valueOf(row.getCell(16).getStringCellValue()));
                        String laboratoireName= (row.getCell(7).getStringCellValue());
                        Laboratoire laboratoire = laboratoireRepo.findByNom(laboratoireName);
                        medicine.setLaboratoire(laboratoire);
                        Stock stock = new Stock();
                        stock.setQuantiteDisponible(medecineDto.getQuantite());
                        stock.setUnite(medecineDto.getUnite());
                        stock.setCreatedBy(username);
                        stock.setLaboratoireNom(laboratoireName);
                        stock.setDateExpiration(medecineDto.getDateExpiration());
                        stock.setDateArrivage(new Date());
                        stock.setLaboratoire(laboratoire);
                        stockRepo.save(stock);
                        medicine.setStock(stock);
                        medecinRepo.save(medicine);
                        return;
                    }
                }
            }
        }

        throw new IllegalArgumentException("Mismatched name! Medicine not found in the Excel data.");
    }




    @Override
    public ResponseEntity<List<Medicaments>> findAllMedecine() {
        try {
            List<Medicaments> productList = medecinRepo.findAll();
            if (!productList.isEmpty()) {
                log.info("List des medicaments est vide");
                return new ResponseEntity(productList, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @Override

    public ResponseEntity<Medicaments> getMedecineById(Long id) {
        Medicaments getMedicaments = medecinRepo.findById(id).get();
        return new ResponseEntity<>(getMedicaments,HttpStatus.OK);
    }
    @Override

    public ResponseEntity<List<Medicaments>> searchMedicine(SearchMedecin searchMedecin) {

        try {
            List productDetails = medecinRepo.findAll()
                    .stream()
                    .filter(showModel -> Objects.equals(searchMedecin.getNom(), showModel.getNom()) ||
                            Objects.equals(searchMedecin.getClasse(), showModel.getClasse()) ||
                            Objects.equals(searchMedecin.getSousClasse(), showModel.getSousClasse()) ||
                            Objects.equals(searchMedecin.getAmm(), showModel.getAmm()) ||
                            Objects.equals(searchMedecin.getIndication(), showModel.getIndication()) ||
                            Objects.equals(searchMedecin.getDosage(), showModel.getDosage()) ||
                            Objects.equals(searchMedecin.getForme(), showModel.getForme())
                    )
                    .limit(5).collect(Collectors.toList());


            return ResponseEntity.status(HttpStatus.OK).body(productDetails);
        }
        catch (Exception exception){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void update(MedecineDto medecineDto,String username, Long id) {
        Medicaments existingMedicaments= medecinRepo.findById(id)
                .orElseThrow(() -> new MedecineNotFoundException(id));
        existingMedicaments.setDateArrivage(medecineDto.getDateArrivage());


        medecinRepo.save(existingMedicaments);
    }
    @Override

    public ResponseEntity<Medicaments> deleteMedicament(Long id) {
        try {
            List medicaments = medecinRepo.findAll().stream()
                    .filter(item-> item.getId().equals(id))
                    .collect(Collectors.toList());
            if(!medicaments.isEmpty()) {
                medecinRepo.deleteById(id);
                return new ResponseEntity<Medicaments>(HttpStatus.OK);

            } else {
                return new ResponseEntity<Medicaments>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<Medicaments>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
    @Override
    public List<Long> retrieveMedecinIdsByNames(List<String> medecinNames) {
        List<Long> medecinIds = new ArrayList<>();
        for (String medecinName : medecinNames) {
            List<Medicaments> medecinList = medecinRepo.findByNom(medecinName);
            for (Medicaments medecin : medecinList) {
                medecinIds.add(medecin.getId());
            }
        }
        return medecinIds;
    }
    @Override
    public boolean isMedicineAvailable(String medicineName) {
        List<Medicaments> medicines = medecinRepo.findByNom(medicineName);

        for (Medicaments medicine : medicines) {
            if (medicine.getStock() != null && medicine.getStock().getQuantiteDisponible() > 0) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isNotExpired(String medicineName) {
        List<Medicaments> medicines = medecinRepo.findByNom(medicineName);
        LocalDate currentDate = LocalDate.now();

        for (Medicaments medicine : medicines) {
            LocalDate expirationDate = medicine.getStock().getDateExpiration();
            if (expirationDate != null) {
                if (expirationDate.isBefore(currentDate)) {
                    return false;
                }
            }
        }

        return true;
    }

    public List<String> retrieveMedicinesFromExcel() throws IOException {
        List<String> medicines = new ArrayList<>();

        ClassPathResource resource = new ClassPathResource("liste_amm_3.xlsx");
        InputStream inputStream = resource.getInputStream();

        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            Cell cell = row.getCell(0);
            if (cell != null && cell.getCellType() == CellType.STRING) {
                String medicine = cell.getStringCellValue().trim();
                if (!medicine.isEmpty()) {
                    medicines.add(medicine);
                }
            }
        }

        workbook.close();
        inputStream.close();

        return medicines;
    }
    public String getDoseByName(String medicineName) throws IOException {
        ClassPathResource resource = new ClassPathResource("liste_amm_3.xlsx");
        InputStream inputStream = resource.getInputStream();

        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            Cell nameCell = row.getCell(0);
            Cell doseCell = row.getCell(1);

            if (nameCell != null && nameCell.getStringCellValue().equalsIgnoreCase(medicineName)) {
                return doseCell != null ? doseCell.getStringCellValue() : "Dose not found";
            }
        }

        return "Medicine not found";
    }

      }
