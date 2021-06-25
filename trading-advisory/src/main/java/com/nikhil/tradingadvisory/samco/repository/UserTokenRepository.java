package com.nikhil.tradingadvisory.samco.repository;

import com.nikhil.tradingadvisory.samco.model.UserTokenModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTokenRepository extends JpaRepository<UserTokenModel, String> {
}
