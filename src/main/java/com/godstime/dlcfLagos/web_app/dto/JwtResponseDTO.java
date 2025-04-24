package com.godstime.dlcfLagos.web_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponseDTO {
    
    private String token;
    
    private String type = "Bearer";
    
    private Long id;
    
    private String email;
    
    private Set<String> roles;
} 