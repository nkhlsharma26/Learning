package com.nikhil.tradingadvisory.TechnicalIndicators.repository;

import com.nikhil.tradingadvisory.TechnicalIndicators.entity.IntraDayCandleDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntraDayCandleDataRepository extends JpaRepository<IntraDayCandleDataEntity, Long> {
}
