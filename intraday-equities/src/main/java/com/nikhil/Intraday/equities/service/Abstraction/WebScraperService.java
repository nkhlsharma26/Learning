package com.nikhil.Intraday.equities.service.Abstraction;

import com.nikhil.Intraday.equities.modal.TechnicalParameters;

import java.util.List;

public interface WebScraperService {
    List<TechnicalParameters> getTechnicalParameters(List<String> scripNames);
}
