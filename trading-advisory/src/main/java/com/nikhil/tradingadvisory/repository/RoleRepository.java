package com.nikhil.tradingadvisory.repository;

import com.nikhil.tradingadvisory.model.Role;
import com.nikhil.tradingadvisory.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
