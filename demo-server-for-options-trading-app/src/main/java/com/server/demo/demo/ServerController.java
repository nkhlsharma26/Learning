package com.server.demo.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.demo.demo.model.*;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

@RestController
public class ServerController {
    private int attempt = 0;
    private int statusAttempt =0;

    @PostMapping("/login")
    public AuthResponse authResponse(@RequestBody UserDetails userDetails) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        AuthResponse response =  objectMapper.readValue(new File("src/main/resources/responses/LoginResponse.json"), AuthResponse.class);
        return response;
    }
    @PostMapping("/order/placeOrder")
        public PlaceOrderResponse placeOrder(@RequestBody PlaceOrder placeOrder) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        PlaceOrderResponse response =  objectMapper.readValue(new File("src/main/resources/responses/PlaceOrderResponse.json"), PlaceOrderResponse.class);
        response.getOrderDetails().setTradingSymbol(placeOrder.getSymbolName());
        response.getOrderDetails().setExchange(placeOrder.getExchange());
        response.getOrderDetails().setTransactionType(placeOrder.getTransactionType());
        response.getOrderDetails().setOrderType(placeOrder.getOrderType());
        response.getOrderDetails().setOrderValidity(placeOrder.getOrderValidity());
        return response;
    }

    @PostMapping("/position/squareOff")
    public SquareOffResponseModel squareOffApiDummy(@RequestBody SquareOffRequestModel squareOffRequestModel) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        SquareOffResponseModel response =  objectMapper.readValue(new File("src/main/resources/responses/PositionSquareOffResponse.json"), SquareOffResponseModel.class);
        int x = squareOffRequestModel.getPositionSquareOffRequestList().size();
        for(int i=1;i<x;i++){
            response.getPositionSquareOffResponseList().add(response.getPositionSquareOffResponseList().get(0));
        }
        return response;
    }

    @GetMapping("/order/orderBook")
    public OrderBookResponse getOrderStatus() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        OrderBookResponse response =  objectMapper.readValue(new File("src/main/resources/responses/OrderBookResponse.json"), OrderBookResponse.class);

        if(statusAttempt == 0){
            response.getOrderBookDetails().get(0).setFilledQuantity((String.valueOf(Integer.parseInt(response.getOrderBookDetails().get(0).getQuantity())-75)));
            statusAttempt++;
        }
        else{
            response.getOrderBookDetails().get(0).setFilledQuantity(response.getOrderBookDetails().get(0).getQuantity());
            statusAttempt=0;
        }
        return response;
    }

    @GetMapping("/option/optionChain")
    public OptionChain getOptionChain(@RequestParam(name = "exchange") String exchange, @RequestParam(name = "searchSymbolName") String searchSymbolName) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        OptionChain response =  objectMapper.readValue(new File("src/main/resources/responses/OptionChainRes.json"), OptionChain.class);
        SpotPrice spotPrice = new SpotPrice();
        spotPrice.setLastPrice(response.getOptionChainDetails().get(0).getSpotPrice());
        spotPrice.setTimeStamp(response.getServerTime());
        return response;
    }

    @GetMapping("/trade/tradeBook")
    public TradeBookResponse getTradeBook() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        TradeBookResponse response =  objectMapper.readValue(new File("src/main/resources/responses/TradeBookResponse"), TradeBookResponse.class);
        return response;
    }
}
