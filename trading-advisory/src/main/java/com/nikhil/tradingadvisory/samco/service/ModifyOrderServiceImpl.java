package com.nikhil.tradingadvisory.samco.service;

import com.nikhil.tradingadvisory.emailService.EmailService;
import com.nikhil.tradingadvisory.samco.Abstraction.ModifyOrderService;
import com.nikhil.tradingadvisory.samco.Abstraction.SamcoAuthService;
import com.nikhil.tradingadvisory.samco.Abstraction.TradeBookService;
import com.nikhil.tradingadvisory.samco.model.*;
import com.nikhil.tradingadvisory.samco.repository.PlaceOrderResponseRepository;
import com.nikhil.tradingadvisory.samco.repository.ReferenceDataRepository;
import com.nikhil.tradingadvisory.samco.repository.UserTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.swing.text.html.HTMLDocument;
import java.sql.Timestamp;
import java.util.*;

@Service
public class ModifyOrderServiceImpl implements ModifyOrderService {
    @Value("${stocknoteURI}")
    private String stockNoteURI;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    DataPollingService dataPollingService;

    @Autowired
    UserTokenRepository userTokenRepository;

    @Autowired
    ReferenceDataService referenceDataService;

    @Autowired
    TradeBookService tradeBookService;

    @Autowired
    SamcoAuthService samcoAuthService;

    @Autowired
    PlaceOrderResponseRepository placeOrderResponseRepository;

    private static final String END_POINT = "/order/modifyOrder/";

    private final static Logger LOGGER = LoggerFactory.getLogger(ModifyOrderServiceImpl.class);

    public void modifyOrder(String fromDate, String toDate, String boughtStock) {

        Map<String, ReferenceData> polledDataForBoughtStock = dataPollingService.pollData(fromDate, toDate,"5", boughtStock);
        ResponseEntity<TradeBookResponse> tradeBookData = tradeBookService.getOrderDetail();

        if(tradeBookData.getBody() != null
                && tradeBookData.getBody().getTradeBookDetails().size() > 0
                && tradeBookData.getBody().getTradeBookDetails().get(0).getOrderStatus().equalsIgnoreCase("Pending"))
        {
            TradeBookDetail data = tradeBookData.getBody().getTradeBookDetails().get(0);

            ReferenceData polledData = polledDataForBoughtStock.get(boughtStock);
            if (requireModification(polledData, data)) {

                double candleClose = Double.parseDouble(polledData.getClose());
                double triggerPrice = candleClose - ((candleClose*1)/100);
                ModifyOrderPayload modifyOrderPayload = new ModifyOrderPayload();
                modifyOrderPayload.setTriggerPrice(String.valueOf(triggerPrice));

                LOGGER.info("Placing order modification request :" + modifyOrderPayload.toString());
                placeModifiedOrder(modifyOrderPayload);
            }
            else{
                LOGGER.info("Nothing to modify.");
            }
        }
        else{
            referenceDataService.deleteBySymbol(boughtStock);
            GlobalUtilities.findStockScheduler = true; //if there is nothing in the trade book, we will start buying stocks
            GlobalUtilities.startOrderModification = false; //stop modification service as there is nothing in the tradebook.
        }

    }

    private void placeModifiedOrder(ModifyOrderPayload modifyOrderPayload) {
        // we have placed same order for all the subscribers, so we can get all the orders from db and modify them using this payload.
        List<PlaceOrderResponseEntity> placedOrders = placeOrderResponseRepository.findAll();
        for (PlaceOrderResponseEntity entity: placedOrders ) {
            String orderNumber = entity.getOrderNumber();
            String modifyOrderUrl = stockNoteURI+END_POINT + "{"+ orderNumber +"}";
            HttpHeaders headers = new HttpHeaders();
            ResponseEntity<ModifyOrderResponse> response = null;
            try{
                UserTokenModel userToken = userTokenRepository.getById(entity.getUserId());
                if(userToken == null || userToken.getToken().isEmpty() || userToken.getToken() == null){
                    userToken.setToken(samcoAuthService.loginUser(entity.getUserId()));
                }
                headers.set("x-session-token", userToken.getToken());
                HttpEntity<ModifyOrderPayload> modifyOrderEntity = new HttpEntity<>(modifyOrderPayload, headers);
                LOGGER.info("Modifying order at :"+ new Timestamp(System.currentTimeMillis()));
                response = restTemplate.exchange(modifyOrderUrl, HttpMethod.PUT, modifyOrderEntity, ModifyOrderResponse.class, orderNumber);

            }
            catch (HttpServerErrorException.InternalServerError e){
                LOGGER.info(e.getMessage());
                if(e.getMessage().contains("Session Expired. Please login again.")){
                    LOGGER.info("Session Expired. Retrying....");
                    LOGGER.info("Getting new token....");
                    String newToken = samcoAuthService.loginUser(entity.getUserId());
                    HttpHeaders newHeaders = new HttpHeaders();
                    newHeaders.set("x-session-token", newToken);
                    HttpEntity<ModifyOrderPayload> newEntity = new HttpEntity<>(newHeaders);
                    response = restTemplate.exchange(modifyOrderUrl, HttpMethod.GET, newEntity, ModifyOrderResponse.class);
                }
            }
            if(response != null){
                LOGGER.info("Order modification response : "+ response.getStatusCodeValue() +", "+response.getBody().toString());
            }
            else{
                LOGGER.error("Something went wrong while modifying order ,"+ response.getStatusCodeValue());
            }

        }

    }

    private boolean requireModification(ReferenceData polledData, TradeBookDetail tradeBookData) {

        return Double.parseDouble(polledData.getClose()) > Double.parseDouble(tradeBookData.getTradePrice());
    }
}