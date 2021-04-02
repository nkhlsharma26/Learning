package com.nikhil.optionstradingapp.service;

import com.nikhil.optionstradingapp.model.*;
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

import java.util.ArrayList;
import java.util.List;

@Service
public class SquareOffService {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    SessionInfo sessionInfo;
    @Autowired
    TradeBookService tradeBookService;

    @Value("${stocknoteURI}")
    private String stockNoteURI;

    private static final String endPoint = "/position/squareOff";

    private Logger logger = LogManager.getLogger(SquareOffService.class);
    public SquareOffResponseModel squareOffPositions(SquareOffRequestModel squareOffRequestModel){
        String squareOffUrl = stockNoteURI+endPoint;
        SquareOffRequestModel requestModel = new SquareOffRequestModel();
        if(squareOffRequestModel == null){
            ResponseEntity<TradeBookResponse> tradeBookResp = tradeBookService.getTradeBook();
            List<TradeBookDetails> tradeBookDetails = tradeBookResp.getBody().getTradeBookDetails();
            List<PositionSquareOffRequestList> list = new ArrayList<>();
            for (TradeBookDetails details: tradeBookDetails) {
                PositionSquareOffRequestList psr = new PositionSquareOffRequestList();
                psr.setExchange(details.getExchange());
                psr.setNetQuantity(details.getFilledQuantity());
                psr.setProductType(details.getProductCode());
                psr.setSymbolName(details.getTradingSymbol());
                psr.setTransactionType(details.getTransactionType());
                list.add(psr);
            }
            requestModel.setPositionSquareOffRequestList(list);
        }
        else {
            requestModel = squareOffRequestModel;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-session-token", sessionInfo.getSessionToken());
        HttpEntity<SquareOffRequestModel> squareOffEntity = new HttpEntity<>(requestModel, headers);
        ResponseEntity<SquareOffResponseModel> response = null;
        logger.info("Placing order for square off: "+squareOffEntity.getBody().getPositionSquareOffRequestList().toString());
        try{
            response = restTemplate.exchange(squareOffUrl, HttpMethod.POST, squareOffEntity, SquareOffResponseModel.class);
        }
        catch (RuntimeException e){
            logger.error("No positions found for this order.",e);
        }
        logger.info("Square off result:"+ response.getStatusCode() +", "+response.getBody().getPositionSquareOffResponseList().toString());
        return response.getBody();
    }
}
