package org.example.service;

import lombok.extern.log4j.Log4j;
import org.example.controller.ApiControllers;
import org.example.dataModel.DocumentResponsePOJO;
import org.example.dataModel.FetchedDataModel;
import org.example.utils.DataStorageImpl;
import org.example.utils.ImageToXlsx;
import org.jsoup.nodes.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j
@EnableScheduling
public class MainServiceImpl implements MainService {

    private final ApiControllers apiControllers;
    private final DataStorageImpl dataStorage;
    private final DocumentResponsePOJO documentResponse;
    private final ImageToXlsx image;

    public MainServiceImpl(ApiControllers apiControllers, DataStorageImpl dataStorage, DocumentResponsePOJO documentResponse,
                           ImageToXlsx image) {
        this.apiControllers = apiControllers;
        this.dataStorage = dataStorage;
        this.documentResponse = documentResponse;

        this.image = image;
    }

    @Override
    @Bean
    @Scheduled(fixedRate = 600000)
    public void htmlToXLS() {
        String html = apiControllers.getResponseFromActivityURL().body().prettyPrint().toString();
        log.debug("MainService: html response received");

        Document document = dataStorage.parseHTMLtoJsoupDocument(html);
        log.debug("MainService: Document created");

        List<FetchedDataModel> scribedData = documentResponse.documentResponsePOJO(document);
        log.debug("MainService: POJO Created");

        dataStorage.switchForCreationUpdatingXlsFile(scribedData);
        log.debug("MainService: html data fetched and extracted to XLS. find in projectDIR");

    }
}
