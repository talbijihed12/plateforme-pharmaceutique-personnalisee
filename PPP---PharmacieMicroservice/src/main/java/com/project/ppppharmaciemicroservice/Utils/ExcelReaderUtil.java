package com.project.ppppharmaciemicroservice.Utils;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

public class ExcelReaderUtil {
    public static Workbook readExcelFile(String filePath) throws IOException {
        try (InputStream inputStream = ExcelReaderUtil.class.getClassLoader().getResourceAsStream(filePath)) {
            return new XSSFWorkbook(inputStream); // Use XSSFWorkbook for OOXML (.xlsx) files
        }
    }
}
