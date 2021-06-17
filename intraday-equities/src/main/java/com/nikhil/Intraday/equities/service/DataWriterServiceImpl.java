package com.nikhil.Intraday.equities.service;

import com.nikhil.Intraday.equities.modal.SymbolInfoModel;
import com.nikhil.Intraday.equities.service.Abstraction.DataWriterService;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DataWriterServiceImpl implements DataWriterService {

    public static Map<String, SymbolInfoModel> dailyStaticData = new HashMap<>();

    public void writeData(List<String[]> data){
        createOrUpdateCsvFile(data);
        writeDataToMap(data, dailyStaticData);
    }

    private void writeDataToMap(List<String[]> data, Map<String, SymbolInfoModel> map) {
        for(String [] d : data){
            map.put(d[0], new SymbolInfoModel(d[0],d[1],d[2],d[3],d[4],d[5]));
        }
    }

    private void createOrUpdateCsvFile(List<String[]> s) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter("src/main/resources/data/StockData.csv"));
            writer.writeNext(new String[]{"SYMBOL", "HIGH", "LOW", "CLOSE", "VOLUME", "% CHANGE"});
            for (String[] str : s) {
                writer.writeNext(str);
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
