package com.nikhil.Intraday.equities.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nikhil.Intraday.equities.modal.*;
import com.nikhil.Intraday.equities.service.Abstraction.PlaceOrderService;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlaceOrderServiceImpl implements PlaceOrderService {
    @Value("${placeOrderEndPoint}")
    private String placeOrderEndPoint;

    @Value("${modifyOrderEndPoint}")
    private String modifyOrderEndPoint;

    @Value("${stocknoteURI}")
    private String stockNoteURI;

    @Autowired
    SessionInfo sessionInfo;

    @Autowired
    RestTemplate restTemplate;

    private final Logger logger = LogManager.getLogger(PlaceOrderServiceImpl.class);

    private static final String EMAIL_DATA_PATH = "../IntraDayEquitiesData/BuyData.csv";
    private static final String ORDER_DETAILS = "../IntraDayEquitiesData/PlaceOrderData.json";

    public Boolean buyStock(PlaceOrder placeOrder, String authToken){
        boolean result = false;
        String placeOrderUrl = stockNoteURI + placeOrderEndPoint;
        HttpHeaders headers = new HttpHeaders();
        logger.info("Order place Object "+ placeOrder.toString());
        String sessionToken  = StringUtils.isEmpty(authToken)?sessionInfo.getSessionToken():authToken;

        headers.set("x-session-token", sessionToken);
        HttpEntity<PlaceOrder> placeOrderEntity = new HttpEntity<>(placeOrder, headers);
        logger.info("Going to buy stock at :"+ new Timestamp(System.currentTimeMillis()));
        ResponseEntity<PlaceOrderResponse> response = restTemplate.exchange(placeOrderUrl, HttpMethod.POST, placeOrderEntity, PlaceOrderResponse.class);
        if(response.getStatusCodeValue() == 200){
            result = true;
            logger.info("Order placed successfully. Details: "+response.getBody().toString());
            GlobalUtilities.findStockScheduler = false; //Stop finding stocks to buy
            GlobalUtilities.startOrderModification = true; //Start order modification service
            GlobalUtilities.boughtStock = placeOrder.getSymbolName();
            logger.info("Stopping stockBuyService, Starting modification Service. Bought "+placeOrder.getSymbolName());

            writeBuyDataToJson(placeOrder, ORDER_DETAILS);
            writeBuyDataToCsv(response.getBody(), EMAIL_DATA_PATH);
        }
        else{
            logger.error("Something went wrong while placing order. Details: "+response.getStatusCodeValue() + "message: "+response.getBody().getStatusMessage());
        }
        return result;
    }

    private void writeBuyDataToJson(PlaceOrder body, String orderDetails) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(ORDER_DETAILS), body);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PlaceOrder getOrderDetails(){
        ObjectMapper objectMapper = new ObjectMapper();
        PlaceOrder placedOrder = null;
        try {
            placedOrder = objectMapper.readValue(new File(ORDER_DETAILS), PlaceOrder.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return placedOrder;
    }

    private void writeBuyDataToCsv(PlaceOrderResponse body, String path) {
        try {
            List<OrderDetails> orderList = new ArrayList<>();
            orderList.add(body.getOrderDetails());
            CSVWriter writer = new CSVWriter(new FileWriter(path));
            ColumnPositionMappingStrategy mappingStrategy=
                    new ColumnPositionMappingStrategy();
            mappingStrategy.setType(OrderDetails.class);

            String[] columns = new String[]{"pendingQuantity", "avgExecutionPrice", "orderPlacedBy", "tradingSymbol", "triggerPrice", "exchange","totalQuantity","expiry","transactionType","productType","orderType","quantity","filledQuantity","orderPrice","filledPrice","exchangeOrderNo","orderValidity","orderTime"};
            writer.writeNext(columns);
            mappingStrategy.setColumnMapping(columns);

            StatefulBeanToCsvBuilder<OrderDetails> builder=
                    new StatefulBeanToCsvBuilder(writer);
            StatefulBeanToCsv beanWriter =
                    builder.withMappingStrategy(mappingStrategy).build();

            beanWriter.write(orderList);
            writer.flush();
            // closing the writer object
            writer.close();
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        }
    }
}
