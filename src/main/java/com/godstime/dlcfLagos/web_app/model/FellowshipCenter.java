package com.godstime.dlcfLagos.web_app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "fellowship_centers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FellowshipCenter {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Size(max = 100)
    private String name;
    
    @NotBlank
    @Size(max = 200)
    private String address;
    
    @Size(max = 100)
    private String lga;
    
    @Size(max = 100)
    private String zone;
    
    @Size(max = 20)
    private String phoneNumber;
    
    @Size(max = 100)
    private String email;
    
    @Size(max = 100)
    private String meetingDay;
    
    @Size(max = 20)
    private String meetingTime;
    
    @Size(max = 100)
    private String venue;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    private Double latitude;
    
    private Double longitude;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coordinator_id")
    private User coordinator;
    
    private boolean isActive = true;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
} 