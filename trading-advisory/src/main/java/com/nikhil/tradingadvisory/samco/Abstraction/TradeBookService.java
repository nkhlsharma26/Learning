package com.nikhil.tradingadvisory.samco.Abstraction;


import com.nikhil.tradingadvisory.samco.model.TradeBookResponse;
import org.springframework.http.ResponseEntity;

public interface TradeBookService {
    ResponseEntity<TradeBookResponse> getOrderDetail();
}
