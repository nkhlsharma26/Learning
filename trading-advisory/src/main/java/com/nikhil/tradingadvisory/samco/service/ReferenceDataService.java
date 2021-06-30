package com.nikhil.tradingadvisory.samco.service;

import com.nikhil.tradingadvisory.samco.model.ReferenceData;
import com.nikhil.tradingadvisory.samco.repository.ReferenceDataRepository;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReferenceDataService {
    @Autowired
    ReferenceDataRepository referenceDataRepository;

    public void saveAll(List<ReferenceData> data){
        referenceDataRepository.saveAll(data);
    }

    public void deleteAll() {
        referenceDataRepository.deleteAll();
    }

    public List<ReferenceData> getReferenceData() {
         return referenceDataRepository.findAll();
    }

    public List<String> getStocksWithPositivePercentageChange() {
        return referenceDataRepository.getScripsWithPositivePercentageChange();
    }

    public List<String> getStocksWithNegativePercentageChange() {
        return referenceDataRepository.getScripsWithNegativeOrZeroPercentageChange();
    }

    public void deleteBySymbol(String boughtStock) {
        referenceDataRepository.deleteBySymbol(boughtStock);
    }
}
