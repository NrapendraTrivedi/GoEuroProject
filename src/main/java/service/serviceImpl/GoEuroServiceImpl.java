package service.serviceImpl;


import model.GoEuroModel;
import com.csvreader.CsvWriter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import service.GoEuroService;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by NrapendraKumar on 12-07-2015.
 */
public class GoEuroServiceImpl implements GoEuroService {

    private static final String URL_SOURCE = "http://api.goeuro.com/api/v2/position/suggest/en/";

    public List<GoEuroModel> convertJsonDataToModel(String cityName) {
        String completeUrl = URL_SOURCE + cityName;
        List<GoEuroModel> goEuroModels = new LinkedList<GoEuroModel>();
        try {
            URL url = new URL(completeUrl);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setDoInput(true);
            if (url.getContent() != null) {
                InputStream is = url.openStream();
                String jsonData = getJsonData(new BufferedReader(new InputStreamReader(is)));
                JSONArray jsonArray = new JSONArray(jsonData);
                int jsonArrayLength = jsonArray.length();
                for (int i = 0; i < jsonArrayLength; i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    GoEuroModel goEuroModel = GoEuroModel.createGoEuroModel();
                    goEuroModel.set_id(Long.parseLong(jsonObject.get("_id").toString()));
                    goEuroModel.setName(jsonObject.get("name").toString());
                    goEuroModel.setType(jsonObject.get("type").toString());
                    goEuroModel.setLatitude(Double.parseDouble(jsonObject.get("geo_position").toString().split(",")[0].split(":")[1]));
                    goEuroModel.setLongitude(Double.parseDouble(jsonObject.get("geo_position").toString().split(",")[1].split(":")[1].split("}")[0]));
                    goEuroModels.add(goEuroModel);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return goEuroModels;
    }

    public String getCsvFilePath() {
        String filePath = null;
        File file = new File("goEuroDirectory");
        if (!file.exists()) {
            file.mkdirs();
        }
        String fileName = "goEuroFile";
        File requiredFile = new File(file, fileName + ".csv");
        if (!requiredFile.exists()) {
            try {
                requiredFile.createNewFile();
                filePath = requiredFile.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            filePath = requiredFile.getAbsolutePath();
        }
        return filePath;
    }

    public void writeModeltoCsvFile(List<GoEuroModel> goEuroModels , String filePath) {
        try {
            CsvWriter csvOutput = new CsvWriter(new OutputStreamWriter(new FileOutputStream(new File(filePath), true), "UTF-8"), ',');
            List<Long> ids = new LinkedList<Long>();
            for(GoEuroModel goEuroModel : goEuroModels) {
                if(!getIds(filePath).contains(goEuroModel.get_id()) && !ids.contains(goEuroModel.get_id())) {
                    ids.add(goEuroModel.get_id());
                    csvOutput.write(((Long) goEuroModel.get_id()).toString());
                    csvOutput.write(goEuroModel.getName());
                    csvOutput.write(goEuroModel.getType());
                    csvOutput.write(((Double)goEuroModel.getLatitude()).toString());
                    csvOutput.write(((Double)goEuroModel.getLongitude()).toString());
                    csvOutput.endRecord();
                }
            }
            csvOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getJsonData(BufferedReader br) {
        String line = null;
        String jsonData = "";
        try {
            while ((line = br.readLine()) != null) {
                jsonData = jsonData + line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonData.trim();
    }

    private List<Long> getIds(String filePath) {
        List<Long> longList = new LinkedList<Long>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line = "";
            while ((line = br.readLine()) != null) {
                longList.add(Long.parseLong(line.split(",")[0]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return longList;
    }
}