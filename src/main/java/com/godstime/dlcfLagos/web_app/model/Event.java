package com.godstime.dlcfLagos.web_app.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "events")
public class Event {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 200)
    private String title;
    
    @Column(nullable = false, length = 2000)
    private String description;
    
    @Column(nullable = false)
    private LocalDateTime startDateTime;
    
    @Column(nullable = false)
    private LocalDateTime endDateTime;
    
    @Column(length = 200)
    private String location;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    @Column(nullable = false)
    private boolean active;
    
    @Column(length = 50)
    private String category; // e.g., FELLOWSHIP, OUTREACH, RETREAT, FAST, etc.
    
    @Column(length = 20)
    private String status; // UPCOMING, ONGOING, COMPLETED, CANCELLED
    
    private Integer maxParticipants;
    
    @Column(length = 50)
    private String targetAudience; // ALL, SPECIFIC_ZONE, SPECIFIC_LGA, SPECIFIC_CDS
    
    @Column(length = 100)
    private String specificTarget; // Zone name, LGA name, or CDS group name if targetAudience is specific
    
    @Column(length = 500)
    private String registrationLink;
    
    @Column(length = 500)
    private String imageUrl;
    
    @ManyToMany
    @JoinTable(
        name = "event_registrations",
        joinColumns = @JoinColumn(name = "event_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> registeredUsers = new HashSet<>();
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        active = true;
        status = "UPCOMING";
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
} 