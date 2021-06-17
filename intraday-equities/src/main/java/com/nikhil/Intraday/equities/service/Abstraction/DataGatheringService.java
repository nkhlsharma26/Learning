package com.nikhil.Intraday.equities.service.Abstraction;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface DataGatheringService {
    List<String[]> getDataForStock(String fromDate, String toDate, String interval, String symbolName, Boolean percentageChangeRequired);
}
