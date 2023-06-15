package service;

import lombok.extern.log4j.Log4j;
import org.example.controller.ApiControllers;
import org.example.dataModel.DocumentResponse;
import org.example.utils.DataStorageImpl;
import org.jsoup.nodes.Document;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

@Service
@Log4j
@EnableScheduling
public class MainServiceImpl implements MainService {

    private final ApiControllers apiControllers;

    private final DataStorageImpl dataStorage;

    public MainServiceImpl(ApiControllers apiControllers, DataStorageImpl dataStorage) {
        this.apiControllers = apiControllers;
        this.dataStorage = dataStorage;
    }

    @Override
    public void htmlToXLS() {
        log.debug("################METHOD EXECUTED#################");
        String html = apiControllers.getResponseFromActivityURL().body().prettyPrint().toString();
        log.debug("MainService: html response received");
        Document documentResponse1 = new DocumentResponse().parseHTMLtoJsoupDocument(html);
        log.debug("MainService: html converted to Document");
        dataStorage.updateXLSFileByDocument(documentResponse1);
    }
}
