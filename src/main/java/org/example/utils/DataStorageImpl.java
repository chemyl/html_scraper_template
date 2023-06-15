package utils;

import lombok.extern.log4j.Log4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
@Log4j
public class DataStorageImpl implements DataStorage {

    @Override
    public Boolean createXLSFilesByDocument(Document response) {
        log.debug("DataStorage: New xls creation Start");
        Workbook workbook = new XSSFWorkbook();
        // Create a new sheet
        Sheet sheet = workbook.createSheet("Response Data");

        // Create a row for the headers
        Row headerRow = sheet.createRow(0);

        // Set the headers
        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellValue("Activity");

        headerCell = headerRow.createCell(1);
        headerCell.setCellValue("Key");

        headerCell = headerRow.createCell(2);
        headerCell.setCellValue("Type");

        // Create a new row for the response data
        Row dataRow = sheet.createRow(headerRow.getRowNum() + 1);
        // Set the response data
        Cell dataCell = dataRow.createCell(0);
        dataCell.setCellValue(response.data().toString());
//        sheet.autoSizeColumn(0);

        dataCell = dataRow.createCell(1);
        dataCell.setCellValue(response.title());
//        sheet.autoSizeColumn(1);

        dataCell = dataRow.createCell(2);
        dataCell.setCellValue(response.title());
//        sheet.autoSizeColumn(2);

        // Save the workbook to a file
        try (FileOutputStream fileOut = new FileOutputStream("response_data.xlsx")) {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Close the workbook
        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.debug("DataStorage: New XLS file created");
        return true;
    }

    @Override
    public Boolean updateXLSFileByDocument(Document response) {
        log.debug("DadaStorage: Start updating");
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream("response_data.xlsx");
            log.debug("DadaStorage: Existed file found!");
        } catch (FileNotFoundException e) {
            createXLSFilesByDocument(response);
        }
        if (fileInputStream != null) {
            log.debug("DataStorage: Existed file updating");
            Workbook workbook = null;
            try {
                workbook = new XSSFWorkbook(fileInputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Sheet sheet = workbook.getSheet("Response Data");
            // Get the last row index to determine the next available row
            int lastRow = sheet.getLastRowNum();
            Row newRow = sheet.createRow(++lastRow);
            log.debug("DataStorage: new Row filled! Row num => " + newRow.getRowNum());

            // Set the response data
            Cell dataCell = newRow.createCell(0);
            dataCell.setCellValue(response.title());
            sheet.autoSizeColumn(0);

            dataCell = newRow.createCell(1);
            dataCell.setCellValue(response.title());
            sheet.autoSizeColumn(1);

            dataCell = newRow.createCell(2);
            dataCell.setCellValue(response.title());
            sheet.autoSizeColumn(2);

            // Save the workbook to a file
            try (
                    FileOutputStream fileOut = new FileOutputStream("response_data.xlsx")) {
                workbook.write(fileOut);
            } catch (
                    IOException e) {
                e.printStackTrace();
            }

            // Close the workbook
            try {
                workbook.close();
            } catch (
                    IOException e) {
                e.printStackTrace();
            }
            log.debug("DataStorage: Existed file updated");
            return true;
        }
        return true;
    }
}
