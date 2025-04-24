package com.godstime.dlcfLagos.web_app.repository;

import com.godstime.dlcfLagos.web_app.model.Role;
import com.godstime.dlcfLagos.web_app.model.Role.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    
    Optional<Role> findByName(ERole name);
} 