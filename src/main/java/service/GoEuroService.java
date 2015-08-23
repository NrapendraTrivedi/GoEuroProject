package service;

import model.GoEuroModel;

import java.util.List;

/**
 * Created by NrapendraKumar on 12-07-2015.
 */
public interface  GoEuroService {

   public List<GoEuroModel> convertJsonDataToModel(String cityName);

   public String getCsvFilePath();

   public void writeModeltoCsvFile(List<GoEuroModel> goEuroModels,String filePath);
}
