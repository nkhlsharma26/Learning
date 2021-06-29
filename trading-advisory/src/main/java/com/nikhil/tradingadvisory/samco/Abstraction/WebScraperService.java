package com.nikhil.tradingadvisory.samco.Abstraction;

import com.nikhil.tradingadvisory.samco.model.TechnicalParameters;

import java.util.List;

public interface WebScraperService {
    List<TechnicalParameters> getTechnicalParameters(List<String> scripNames);
}
