package com.nikhil.tradingadvisory.samco.repository;

import com.nikhil.tradingadvisory.samco.model.ReferenceData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReferenceDataRepository extends JpaRepository<ReferenceData, String> {

    @Query(value = "select symbol from reference_data where percentage > 0", nativeQuery = true)
    List<String> getScripsWithPositivePercentageChange();

    @Query(value = "select symbol from reference_data where percentage <= 0", nativeQuery = true)
    List<String> getScripsWithNegativeOrZeroPercentageChange();

    void deleteBySymbol(String boughtStock);
}
