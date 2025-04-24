package com.godstime.dlcfLagos.web_app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    
    private Long id;
    
    @NotBlank
    @Size(max = 50)
    private String firstName;
    
    @NotBlank
    @Size(max = 50)
    private String lastName;
    
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    
    @Size(max = 20)
    private String phoneNumber;
    
    @Size(max = 100)
    private String lga;
    
    @Size(max = 100)
    private String cdsGroup;
    
    @Size(max = 200)
    private String placeOfPrimaryAssignment;
    
    @Size(max = 100)
    private String unitInFellowship;
    
    @Size(max = 100)
    private String spiritualStatus;
    
    @Size(max = 100)
    private String zone;
    
    @Size(max = 255)
    private String profilePictureUrl;
    
    private Set<String> roles;
} 