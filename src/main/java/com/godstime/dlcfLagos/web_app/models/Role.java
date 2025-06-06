package com.godstime.dlcfLagos.web_app.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false, unique = true)
    private ERole name;
    
    public enum ERole {
        ROLE_USER,
        ROLE_ADMIN,
        ROLE_COORDINATOR,
        ROLE_ZONAL_COORDINATOR,
        ROLE_ASSOCIATE_COORDINATOR
    }
} 