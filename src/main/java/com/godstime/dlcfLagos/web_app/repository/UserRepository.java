package com.godstime.dlcfLagos.web_app.repository;

import com.godstime.dlcfLagos.web_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByEmail(String email);
    
    Boolean existsByEmail(String email);
    
    List<User> findByLga(String lga);
    
    List<User> findByZone(String zone);
    
    List<User> findByCdsGroup(String cdsGroup);
    
    List<User> findByUnitInFellowship(String unitInFellowship);
} 