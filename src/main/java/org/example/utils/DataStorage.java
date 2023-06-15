package utils;

import org.jsoup.nodes.Document;

public interface DataStorage {

    Boolean createXLSFilesByDocument(Document document);

    Boolean updateXLSFileByDocument(Document document);
}
