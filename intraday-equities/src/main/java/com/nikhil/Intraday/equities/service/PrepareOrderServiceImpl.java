package com.nikhil.Intraday.equities.service;

import com.nikhil.Intraday.equities.modal.PlaceOrder;
import com.nikhil.Intraday.equities.modal.SymbolInfoModel;
import com.nikhil.Intraday.equities.service.Abstraction.PlaceOrderService;
import com.nikhil.Intraday.equities.service.Abstraction.PrepareOrderService;
import com.nikhil.Intraday.equities.service.Abstraction.StockSelectionService;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PrepareOrderServiceImpl implements PrepareOrderService {

    @Value("${placeOrderEndPoint}")
    private String placeOrderEndPoint;

    @Value("#{new Double('${amount}')}")
    private Double amount;

    @Value("${stocknoteURI}")
    private String stockNoteURI;

    @Autowired
    StockSelectionService stockSelectionService;

    @Autowired
    DataPollingService dataPollingService;

    @Autowired
    PlaceOrderService placeOrderService;

    private final Logger logger = LogManager.getLogger(PrepareOrderServiceImpl.class);
    private static final String FILE_NAME_WITH_PATH = "src/main/resources/data/StockData.csv";
    protected static final Map<String, SymbolInfoModel> csvData = getCsvData();


    public void getScripToPlaceOrder(String fromDate, String toDate, String symbol) {
        Map<String, SymbolInfoModel> polledData = dataPollingService.pollData(fromDate, toDate, symbol);// symbol is null as we need data for all symbols in the csv
        Map<String, SymbolInfoModel> selectedStock = stockSelectionService.selectStockToBuy(csvData, polledData);
        if (selectedStock.size() > 0) {
            int stockQuantity = calculateOrderQuantity(Double.parseDouble(selectedStock.values().stream().findFirst().get().getClose()));
            PlaceOrder orderPayload = preparePlaceOrderPayload(selectedStock, stockQuantity);
            placeOrderService.buyStock(orderPayload);
        }
    }

    private static Map<String, SymbolInfoModel> getCsvData(){
        return readCsvDataToMap();
    }

    private int calculateOrderQuantity(double pricePerShare) {
        int quantity;
        quantity = (int) (amount/pricePerShare);
        return quantity;
    }

    private PlaceOrder preparePlaceOrderPayload(Map<String, SymbolInfoModel> selectedStock, int stockQuantity) {
        PlaceOrder order  = new PlaceOrder();

        Map.Entry<String, SymbolInfoModel> data = selectedStock.entrySet().iterator().next();
        double price  = Double.parseDouble(data.getValue().getClose());
        double triggerPrice = price - ((price*1)/100);

        order.setSymbolName(data.getKey());
        order.setExchange("NSE");
        order.setTransactionType(getOrderType());
        order.setOrderType("MKT");
        order.setQuantity(String.valueOf(stockQuantity));
        //order.setDisclosedQuantity();
        order.setPrice(String.valueOf(price));
        order.setPriceType("LTP");
        order.setMarketProtection("1");
        order.setOrderValidity("DAY");
        order.setAfterMarketOrderFlag("NO");
        order.setProductType("MIS");
        order.setTriggerPrice(String.valueOf(triggerPrice));
        return order;
    }

    private String getOrderType() {

        return "BUY";
    }

    private static Map<String, SymbolInfoModel> readCsvDataToMap() {
        CSVReader reader;
        Map<String, SymbolInfoModel> csvData = new HashMap<>();
        try {

            reader = new CSVReader(new FileReader(PrepareOrderServiceImpl.FILE_NAME_WITH_PATH));
            List<String[]> allRows = reader.readAll();
            allRows.remove(0); // remove headers
            for(String[] row : allRows){
                String[] data = row[0].split(",");
                SymbolInfoModel sp = new SymbolInfoModel(data[0], data[1],data[2],data[3], data[4]);
                csvData.put(data[0],sp);
            }
        } catch (IOException | CsvException e) {
           e.printStackTrace();
        }
        return csvData;
    }
}
