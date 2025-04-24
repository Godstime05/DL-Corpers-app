package com.godstime.dlcfLagos.web_app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementDTO {
    
    private Long id;
    
    @NotBlank
    @Size(max = 200)
    private String title;
    
    @NotBlank
    @Size(max = 2000)
    private String content;
    
    private Long createdBy;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    private boolean isActive;
    
    private String priority; // HIGH, MEDIUM, LOW
    
    private String targetAudience; // ALL, SPECIFIC_ZONE, SPECIFIC_LGA, SPECIFIC_CDS
    
    private String specificTarget; // Zone name, LGA name, or CDS group name if targetAudience is specific
} 