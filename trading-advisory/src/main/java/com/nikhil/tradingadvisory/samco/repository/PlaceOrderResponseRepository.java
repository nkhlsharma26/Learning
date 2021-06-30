package com.nikhil.tradingadvisory.samco.repository;

import com.nikhil.tradingadvisory.samco.model.PlaceOrderResponseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceOrderResponseRepository extends JpaRepository<PlaceOrderResponseEntity, String> {
}
