package com.nikhil.tradingadvisory.samco.repository;

import com.nikhil.tradingadvisory.model.SamcoUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SamcoUserRepository extends JpaRepository<SamcoUserDetails, Long> {
    Optional<SamcoUserDetails> findByUserId(String userName);
}
