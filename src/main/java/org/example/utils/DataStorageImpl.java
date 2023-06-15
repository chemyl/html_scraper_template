package org.example.utils;

import lombok.extern.log4j.Log4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.dataModel.FetchedDataModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@Log4j
@Configuration
public class DataStorageImpl implements DataStorage {

    @Autowired
    private Environment environment;
    private final ImageToXlsx imageToXlsx;

    public DataStorageImpl(ImageToXlsx imageToXlsx) {
        this.imageToXlsx = imageToXlsx;
    }


    public Document parseHTMLtoJsoupDocument(String html) {
        return Jsoup.parse(html);
    }

    @Override
    public void switchForCreationUpdatingXlsFile(List<FetchedDataModel> products) {
        log.debug("DadaStorage: Start updating");
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(environment.getProperty("key.word") + ".xlsx");
            log.debug("DadaStorage: Existed file found!");
        } catch (FileNotFoundException e) {
            createXLSFilesByDocument(products);
        }
        if (fileInputStream != null) {
            updatingXLSFilesByDocument(products);
        }
    }

    @Override
    public void createXLSFilesByDocument(List<FetchedDataModel> products) {
        log.debug("DataStorage: New xls creation Start");
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Response Data");
        Row headerRow = sheet.createRow(0);

        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellValue("Price");

        headerCell = headerRow.createCell(1);
        headerCell.setCellValue("Title");

        headerCell = headerRow.createCell(2);
        headerCell.setCellValue("ShipCost");

        headerCell = headerRow.createCell(3);
        headerCell.setCellValue("Location");

        headerCell = headerRow.createCell(4);
        headerCell.setCellValue("Image");

        int rowNum = 1;

        for (FetchedDataModel product : products) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(product.getPrice().replaceAll("THB", "THB "));
            sheet.autoSizeColumn(0);
            row.createCell(1).setCellValue(product.getTitle());
            sheet.autoSizeColumn(1);
            row.createCell(2).setCellValue(product.getShipCost().replaceAll("'+'THB", "THB "));
            sheet.autoSizeColumn(2);
            row.createCell(3).setCellValue(product.getLocation());
            sheet.autoSizeColumn(3);
            Cell imageCell = row.createCell(4);
            imageToXlsx.saveImageToXlS(product, workbook, sheet, row, imageCell);
        }
        // Save the workbook to a file
        try (FileOutputStream fileOut = new FileOutputStream(environment.getProperty("key.word") + ".xlsx")) {
            workbook.write(fileOut);
            System.out.println("File saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Close the workbook
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void updatingXLSFilesByDocument(List<FetchedDataModel> products) {
        log.debug("DataStorage: Existed file updating");
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook(environment.getProperty("key.word") + ".xlsx");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Sheet sheet = workbook.getSheet("Response Data");
        int lastRow = sheet.getLastRowNum();

        for (FetchedDataModel product : products) {
            Row row = sheet.createRow(lastRow++);
            row.createCell(0).setCellValue(product.getPrice().replaceAll("THB", "THB "));
            sheet.autoSizeColumn(0);
            row.createCell(1).setCellValue(product.getTitle());
            sheet.autoSizeColumn(1);
            row.createCell(2).setCellValue(product.getShipCost().replaceAll("'+'THB", "THB "));
            sheet.autoSizeColumn(2);
            row.createCell(3).setCellValue(product.getLocation());
            sheet.autoSizeColumn(3);
            Cell imageCell = row.createCell(4);
            imageToXlsx.saveImageToXlS(product, workbook, sheet, row, imageCell);
        }
        try (FileOutputStream fileOut = new FileOutputStream(environment.getProperty("key.word") + ".xlsx")) {
            workbook.write(fileOut);
            System.out.println("File saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Close the workbook
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        log.debug("DataStorage: Existed file updated");
    }
}
