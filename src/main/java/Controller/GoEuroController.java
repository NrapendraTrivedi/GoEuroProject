package Controller;

import Model.GoEuroModel;
import service.GoEuroService;
import service.serviceImpl.GoEuroServiceImpl;

import java.util.List;

/**
 * Created by NrapendraKumar on 12-07-2015.
 */
public class GoEuroController {

    private static GoEuroService goEuroService=new GoEuroServiceImpl();
    public static void main(String[] args) {
        String cityName=args[0];
        List<GoEuroModel> goEuroModels=goEuroService.convertJsonDataToModel(cityName);
        String filePath=goEuroService.getCsvFilePath();
        goEuroService.writeModeltoCsvFile(goEuroModels,filePath);
    }
}
