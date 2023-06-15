package org.example.utils;

import org.example.dataModel.FetchedDataModel;

import java.util.List;

public interface DataStorage {

    void switchForCreationUpdatingXlsFile(List<FetchedDataModel> products);

    void createXLSFilesByDocument(List<FetchedDataModel> products);

    void updatingXLSFilesByDocument(List<FetchedDataModel> products);

}
