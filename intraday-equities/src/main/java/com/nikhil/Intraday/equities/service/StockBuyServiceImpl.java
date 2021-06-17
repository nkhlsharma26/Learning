package com.nikhil.Intraday.equities.service;

import com.nikhil.Intraday.equities.modal.PlaceOrder;
import com.nikhil.Intraday.equities.modal.SymbolInfoModel;
import com.nikhil.Intraday.equities.service.Abstraction.PlaceOrderService;
import com.nikhil.Intraday.equities.service.Abstraction.StockBuyService;
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
public class StockBuyServiceImpl implements StockBuyService {

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

    private final Logger logger = LogManager.getLogger(StockBuyServiceImpl.class);
    private static final String FILE_NAME_WITH_PATH = "src/main/resources/data/StockData.csv";
    protected static Map<String, SymbolInfoModel> csvData = null;

    public void getScripToPlaceOrder(String fromDate, String toDate, String symbol) {
        Map<String, SymbolInfoModel> polledData = dataPollingService.pollData(fromDate, toDate, symbol);// symbol is null as we need data for all symbols in the csv
        if(csvData == null){
            csvData = getCsvData();
        }
        SymbolInfoModel selectedStock = stockSelectionService.selectStockToBuy(csvData, polledData);
        if (selectedStock != null) {
            int stockQuantity = calculateOrderQuantity(Double.parseDouble(selectedStock.getClose()));
            PlaceOrder orderPayload = preparePlaceOrderPayload(selectedStock, stockQuantity);
            placeOrderService.buyStock(orderPayload, null);
        }
        else {
            logger.info("Winner not found.");
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

    private PlaceOrder preparePlaceOrderPayload(SymbolInfoModel selectedStock, int stockQuantity) {
        PlaceOrder order  = new PlaceOrder();

        double price  = Double.parseDouble(selectedStock.getClose());
        //double triggerPrice = price - ((price*1)/100);

        order.setSymbolName(selectedStock.getSymbol());
        order.setExchange("NSE");
        order.setTransactionType(getOrderType());
        order.setOrderType("MKT");
        order.setQuantity(String.valueOf(stockQuantity));
        //order.setDisclosedQuantity();
        order.setPrice(String.valueOf(price));
        order.setPriceType("LTP");
        order.setMarketProtection("1");
        order.setOrderValidity("IOC");
        order.setAfterMarketOrderFlag("NO");
        order.setProductType("MIS");
        //order.setTriggerPrice(String.valueOf(triggerPrice));
        return order;
    }

    private String getOrderType() {

        return "BUY";
    }

    private static Map<String, SymbolInfoModel> readCsvDataToMap() {
        CSVReader reader;
        Map<String, SymbolInfoModel> csvData = new HashMap<>();
        try {

            reader = new CSVReader(new FileReader(StockBuyServiceImpl.FILE_NAME_WITH_PATH));
            List<String[]> allRows = reader.readAll();
            allRows.remove(0); // remove headers
            for(String[] row : allRows){
                SymbolInfoModel sp = new SymbolInfoModel(row[0], row[1],row[2],row[3], row[4],row[5]);
                csvData.put(row[0],sp);
            }
        } catch (IOException | CsvException e) {
           e.printStackTrace();
        }
        return csvData;
    }
}
