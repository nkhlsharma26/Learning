package com.nikhil.tradingadvisory.repository;

import com.nikhil.tradingadvisory.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username, String email);

    List<User> findByIdIn(List<Long> userIds);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "UPDATE users a SET a.is_active = TRUE WHERE a.email = ?1",nativeQuery = true)
    int enableUser(String email);

    @Transactional
    @Modifying
    @Query(value = "UPDATE users a SET a.samco_user_details_id = ?2 WHERE a.username = ?1",nativeQuery = true)
    void updateSamcoDetailsId(String username, Long id);

    @Query(value = "select email from users where is_active = true",nativeQuery = true)
    String[] getEmailsForRegisteredUser();
}
