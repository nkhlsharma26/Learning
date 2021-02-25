package com.nikhil.optionstradingapp.service;

import com.nikhil.optionstradingapp.event.PutLongOrderEvent;
import com.nikhil.optionstradingapp.event.ShortOrderEvent;
import com.nikhil.optionstradingapp.model.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.stream.Collectors;

@Service
public class ShortOrderService implements ApplicationListener<ShortOrderEvent> {
    @Value("${stocknoteURI}")
    private String stockNoteURI;

    @Value("${symbolNamePrefix}")
    private String symbolNamePrefix;

    @Value("${symbolNameSuffixCall}")
    private String symbolNameSuffixCall;

    @Value("${symbolNameSuffixPut}")
    private String symbolNameSuffixPut;
    
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
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    PlaceOrderService placeOrderService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    TradeBookService tradeBookService;

    private static final String endPoint = "/order/placeOrder";

    private Logger logger = LogManager.getLogger(ShortOrderService.class);

    public ResponseEntity<PlaceOrderResponse> placeShortOrder(Integer quantity, String lastLongTradingSymbol){
        String placeOrderUrl = stockNoteURI+endPoint;
        SpotPrice spotPrice = spotPriceService.fetchSpotPrice();
        double spotPriceValue = Double.parseDouble(spotPrice.getLastPrice());
        PlaceOrder order = prepareOrderDetails(spotPriceValue, lastLongTradingSymbol, quantity);

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-session-token", sessionInfo.getSessionToken());
        HttpEntity<PlaceOrder> placeOrderEntity = new HttpEntity<>(order, headers);
        logger.info("Going to place Short order at :"+ new Timestamp(System.currentTimeMillis()));
        ResponseEntity<PlaceOrderResponse> response = placeOrderService.placeOrderRequest(placeOrderUrl, placeOrderEntity);
        OrderDetails orderDetails = response.getBody().getOrderDetails();
        if(response.getStatusCode().is2xxSuccessful() && orderDetails.getTradingSymbol().contains("CE")){
            OrderEventData eventData = new OrderEventData();
            eventData.setQuantity(Integer.parseInt(orderDetails.getFilledQuantity()));
            eventData.setTransactionType(orderDetails.getTransactionType());
            eventData.setTradingSymbol(orderDetails.getTradingSymbol());
            PutLongOrderEvent putLongOrderEvent = new PutLongOrderEvent(this, eventData);
            applicationEventPublisher.publishEvent(putLongOrderEvent);
        }
        return response;
    }

    private PlaceOrder prepareOrderDetails(double spotPrice, String lastLongTradingSymbol, Integer quantity){

        PlaceOrder placeOrder = new PlaceOrder();
        placeOrder.setSymbolName(createSymbolName(spotPrice, lastLongTradingSymbol));
        placeOrder.setExchange(Exchange.NFO.toString());
        placeOrder.setTransactionType(TransactionType.SELL.toString());
        placeOrder.setOrderType(OrderType.MKT.toString());
        placeOrder.setQuantity(String.valueOf(quantity));
        placeOrder.setDisclosedQuantity(disclosedQuantity);
        placeOrder.setOrderValidity(orderValidity);
        placeOrder.setProductType(productType);
        placeOrder.setAfterMarketOrderFlag(afterMarketOrderFlag);

        return placeOrder;
    }

    private String createSymbolName(double spotPrice, String lastLongTradingSymbol){
        String symbolSuffix = lastLongTradingSymbol.contains("CE") ? symbolNameSuffixCall : symbolNameSuffixPut;
        String strikePrice = getStrikePriceForSymbolName(spotPrice);
        String targetDate = getDateForSymbolName();
        String symbolName = symbolNamePrefix+targetDate+strikePrice+symbolSuffix;
        logger.info("Generated Strike Price: "+strikePrice);
        logger.info("Generated target date: "+targetDate);
        logger.info("Created symbol Name: "+symbolName);
        return symbolName;
    }

    private String getDateForSymbolName(){
        LocalDate dt = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMMyy");
        LocalDate nextThursday = dt.with(TemporalAdjusters.nextOrSame(DayOfWeek.THURSDAY));
        return nextThursday.format(formatter).toUpperCase();
    }

    private String getStrikePriceForSymbolName(double spotPrice){
        double approxStrikePrice = spotPrice;
        double diff = approxStrikePrice % 50;
        int strikePrice = (int) (diff>=25 ? (approxStrikePrice+(50 - diff)):(approxStrikePrice-diff));
        return String.valueOf(strikePrice);
    }

    @Override
    public void onApplicationEvent(ShortOrderEvent orderEvent) {
        OrderEventData data = orderEvent.getData();
        if(data.getQuantity() > 0){
            placeShortOrder(data.getQuantity(), data.getTradingSymbol());
        }
    }

}
