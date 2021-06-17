package com.nikhil.Intraday.equities.service.Abstraction;

import com.nikhil.Intraday.equities.modal.SymbolInfoModel;

import java.util.Map;

public interface StockSelectionService {
    SymbolInfoModel selectStockToBuy(Map<String, SymbolInfoModel> csvData, Map<String, SymbolInfoModel> polledData);
}
