package com.nikhil.tradingadvisory.samco.Abstraction;

import com.nikhil.tradingadvisory.samco.model.ReferenceData;

import java.util.Map;

public interface StockSelectionService {
    ReferenceData selectStockToBuy(String fromData, String toDate);
}
