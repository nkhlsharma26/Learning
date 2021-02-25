package com.nikhil.optionstradingapp.service;

import com.nikhil.optionstradingapp.event.PutLongOrderEvent;
import com.nikhil.optionstradingapp.event.ShortOrderEvent;
import com.nikhil.optionstradingapp.model.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

@Service
public class LongPutOrderService {

    @Value("${stocknoteURI}")
    private String stockNoteURI;

    @Value("${symbolNamePrefix}")
    private String symbolNamePrefix;

    @Value("${symbolNameSuffixPut}")
    private String symbolNameSuffixPut;

    /*@Value("${numberOfLots}")
    private int numberOfLots;*/

    @Value("${exchange}")
    private String exchange;

    @Value("${transactionType}")
    private String transactionType;

    @Value("${orderType}")
    private String orderType;

    @Value("${disclosedQuantity}")
    private String disclosedQuantity;

    @Value("${orderValidity}")
    private String orderValidity;

    @Value("${productType}")
    private String productType;

    @Value("${afterMarketOrderFlag}")
    private String afterMarketOrderFlag;

    @Autowired
    SpotPriceService spotPriceService;
    @Autowired
    SessionInfo sessionInfo;

    @Autowired
    PlaceOrderService placeOrderService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    private static final String endPoint = "/order/placeOrder";
    //private static final int lotSize = 75;

    private Logger logger = LogManager.getLogger(LongPutOrderService.class);

    public ResponseEntity<PlaceOrderResponse> placeLongPutOrder(Integer quantity){
        String placeOrderUrl = stockNoteURI+endPoint;
        SpotPrice spotPrice = spotPriceService.fetchSpotPrice();
        double spotPriceValue = Double.parseDouble(spotPrice.getLastPrice());
        PlaceOrder order = prepareOrderDetails(spotPriceValue, quantity);

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-session-token", sessionInfo.getSessionToken());
        HttpEntity<PlaceOrder> placeOrderEntity = new HttpEntity<>(order, headers);
        logger.info("Going to place Long Put order at :"+ new Timestamp(System.currentTimeMillis()));
        ResponseEntity<PlaceOrderResponse> response = placeOrderService.placeOrderRequest(placeOrderUrl, placeOrderEntity);
        if(response.getStatusCode().is2xxSuccessful()) {
            OrderEventData eventData = new OrderEventData();
            eventData.setQuantity(Integer.parseInt(response.getBody().getOrderDetails().getFilledQuantity()));
            eventData.setTradingSymbol(response.getBody().getOrderDetails().getTradingSymbol());
            ShortOrderEvent shortOrderEvent = new ShortOrderEvent(this, eventData);
            applicationEventPublisher.publishEvent(shortOrderEvent);
        }
        return response;
    }
    private PlaceOrder prepareOrderDetails(double spotPrice, Integer quantity){

        PlaceOrder placeOrder = new PlaceOrder();
        placeOrder.setSymbolName(createSymbolName(spotPrice));
        placeOrder.setExchange(Exchange.NFO.toString());
        placeOrder.setTransactionType(TransactionType.BUY.toString());
        placeOrder.setOrderType(OrderType.MKT.toString());
        placeOrder.setQuantity(String.valueOf(quantity));
        placeOrder.setDisclosedQuantity(disclosedQuantity);
        placeOrder.setOrderValidity(orderValidity);
        placeOrder.setProductType(productType);
        placeOrder.setAfterMarketOrderFlag(afterMarketOrderFlag);

        return placeOrder;
    }

    private String createSymbolName(double spotPrice){
        String strikePrice = getStrikePriceForSymbolName(spotPrice);
        String targetDate = getDateForSymbolName();
        String symbolName = symbolNamePrefix+targetDate+strikePrice+symbolNameSuffixPut;
        logger.info("Generated Strike Price: "+strikePrice);
        logger.info("Generated target date: "+targetDate);
        logger.info("Created symbol Name: "+symbolName);
        return symbolName;
    }

    private String getDateForSymbolName(){
        LocalDate dt = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMM");
        LocalDate nextThursday = dt.with(TemporalAdjusters.nextOrSame(DayOfWeek.THURSDAY));
        LocalDate lastThursday = dt.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));
        if(nextThursday.isBefore(lastThursday)){
            return lastThursday.format(formatter).toUpperCase();
        }
        else{
            LocalDate lastThursdayOfNextMonth =dt.plusMonths(1).with(TemporalAdjusters.nextOrSame(DayOfWeek.THURSDAY));
            return lastThursdayOfNextMonth.format(formatter).toUpperCase();
        }
    }

    private String getStrikePriceForSymbolName(double spotPrice){
        double standardDiff = 200;
        double approxStrikePrice = spotPrice + standardDiff;
        double diff = approxStrikePrice % 100;
        int strikePrice = (int) (diff>=40 ? (approxStrikePrice+(100 - diff)):(approxStrikePrice-diff));

        return String.valueOf(strikePrice);
    }

    @EventListener
    public void handleEvent(PutLongOrderEvent putLongOrderEvent){

        placeLongPutOrder(putLongOrderEvent.getData().getQuantity());

    }
}
