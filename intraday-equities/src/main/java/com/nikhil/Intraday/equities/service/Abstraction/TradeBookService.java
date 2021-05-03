package com.nikhil.Intraday.equities.service.Abstraction;

import com.nikhil.Intraday.equities.modal.TradeBookResponse;
import org.springframework.http.ResponseEntity;

public interface TradeBookService {
    ResponseEntity<TradeBookResponse> getOrderDetail();
}
