package com.nikhil.tradingadvisory.samco.Abstraction;

import com.nikhil.tradingadvisory.samco.model.ReferenceData;

import java.util.List;

public interface DataGatheringService {
    List<ReferenceData> getDataForStock(String fromDate, String toDate, String interval, List<String> symbolName, boolean referenceData);
}
