package org.example.utils;

import lombok.extern.log4j.Log4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.example.dataModel.FetchedDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

@Service
@Log4j
public class ImageToXlsx {

    @Autowired
    private Environment environment;

    public Boolean saveImageToXlS(FetchedDataModel product, Workbook workbook, Sheet sheet, Row row, Cell cell) {
        if (!product.getImage().isEmpty()) {
            log.debug("SaveImageToXLS: new Image saved");
            URL imageUrl;
            try {
                imageUrl = new URL(product.getImage());
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            InputStream imageStream = null;
            try {
                imageStream = imageUrl.openStream();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            byte[] imageData;
            try {
                imageData = IOUtils.toByteArray(imageStream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            BufferedImage bufferedImage;
            try {
                bufferedImage = ImageIO.read(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Drawing<?> drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 0, 1, 1, 2);

            int pictureIndex = workbook.addPicture(imageData, Workbook.PICTURE_TYPE_JPEG);
            anchor.setCol1(cell.getColumnIndex());
            anchor.setRow1(row.getRowNum());

            Picture picture = drawing.createPicture(anchor, pictureIndex);
            picture.resize();

            sheet.setColumnWidth(4, (bufferedImage.getWidth()));
            row.setHeightInPoints(bufferedImage.getHeight() * 0.2f);

//        try (FileOutputStream fileOut = new FileOutputStream(environment.getProperty("key.word") + ".xlsx")) {
//            workbook.write(fileOut);
//            System.out.println("File saved successfully.");
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            // Close the workbook
//            try {
//                workbook.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        }
        return null;
    }
}