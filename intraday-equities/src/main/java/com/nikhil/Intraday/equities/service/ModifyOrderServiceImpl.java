package com.nikhil.Intraday.equities.service;

import com.nikhil.Intraday.equities.modal.*;
import com.nikhil.Intraday.equities.service.Abstraction.ModifyOrderService;
import com.nikhil.Intraday.equities.service.Abstraction.TradeBookService;
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

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class ModifyOrderServiceImpl implements ModifyOrderService {
    @Value("${stocknoteURI}")
    private String stockNoteURI;
    @Autowired
    SessionInfo sessionInfo;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    DataPollingService dataPollingService;

    @Autowired
    TradeBookService tradeBookService;

    private static final String END_POINT = "/order/modifyOrder/";

    private final Logger logger = LogManager.getLogger(ModifyOrderServiceImpl.class);

    public void modifyOrder(String fromDate, String toDate, String boughtStock) {

        Map<String, SymbolInfoModel> polledData = dataPollingService.pollData(fromDate, toDate, boughtStock);
        ResponseEntity<TradeBookResponse> tradeBookData = tradeBookService.getOrderDetail();

        if(tradeBookData.getBody().getTradeBookDetails().size() > 0){
            TradeBookDetail data = Objects.requireNonNull(tradeBookData.getBody()).getTradeBookDetails().get(0);
            Map<String, String> tradeBookStock = new HashMap<>();
            tradeBookStock.put(data.getTradingSymbol(), data.getTradePrice());

            if (requireModification(polledData, tradeBookStock)) {

                SymbolInfoModel symbolInfoModel = polledData.entrySet().stream().findFirst().get().getValue();
                //double price = Double.parseDouble(symbolInfoModel.getClose());
                //double triggerPrice = price - ((price * 1) / 100);

                ModifyOrderPayload modifyOrderPayload = new ModifyOrderPayload();
                modifyOrderPayload.setMarketProtection("1");
                modifyOrderPayload.setOrderValidity(data.getOrderValidity());
                //modifyOrderPayload.setPrice(String.valueOf(price));
                //modifyOrderPayload.setTriggerPrice(String.valueOf(triggerPrice));
                //modifyOrderPayload.setDisclosedQuantity(data.getDisclosedQuantity());
                //modifyOrderPayload.setQuantity(data.getQuantity());
                //modifyOrderPayload.setOrderType(data.getOrderType());
                //modifyOrderPayload.setOrderValidity(data.getOrderValidity());

                logger.info("Placing order modification request :" + modifyOrderPayload.toString());
                placeModifiedOrder(modifyOrderPayload, data.getOrderNumber());
            }
            else{
                logger.info("Nothing to modify.");
            }
        }
        else{
            StockBuyServiceImpl.csvData.remove(boughtStock);
            GlobalUtilities.findStockScheduler = true; //if there is nothing in the trade book, we will start buying stocks
            GlobalUtilities.startOrderModification = false; //stop modification service as there is nothing in the tradebook.
            logger.info("Starting Stock buy service.\n Stopping Modification service. removing "+boughtStock+" from csvData list.");
        }

    }

    private ResponseEntity<ModifyOrderResponse> placeModifiedOrder(ModifyOrderPayload modifyOrderPayload, String orderNumber) {

        String modifyOrderUrl = stockNoteURI+END_POINT + "{"+orderNumber+"}";
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-session-token", sessionInfo.getSessionToken());
        HttpEntity<ModifyOrderPayload> modifyOrderEntity = new HttpEntity<>(modifyOrderPayload, headers);
        logger.info("Modifying order at :"+ new Timestamp(System.currentTimeMillis()));
        ResponseEntity<ModifyOrderResponse> response = restTemplate.exchange(modifyOrderUrl, HttpMethod.PUT, modifyOrderEntity, ModifyOrderResponse.class, orderNumber);
        return response;
    }

    private boolean requireModification(Map<String, SymbolInfoModel> polledData, Map<String, String> tradeBookStock) {
        Optional<String> key = polledData.keySet().stream().findFirst();
        return Double.parseDouble(polledData.get(key.get()).getClose()) > Double.parseDouble(tradeBookStock.get(key.get()));
    }
}