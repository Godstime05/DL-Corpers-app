package com.godstime.dlcfLagos.web_app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "evangelism_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvangelismRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Size(max = 100)
    private String title;
    
    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Size(max = 200)
    private String location;
    
    private LocalDateTime eventDate;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;
    
    private int soulsWon;
    
    private int followUpCount;
    
    @ManyToMany
    @JoinTable(name = "evangelism_participants",
            joinColumns = @JoinColumn(name = "evangelism_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> participants = new HashSet<>();
    
    @Column(columnDefinition = "TEXT")
    private String testimonies;
    
    @Column(columnDefinition = "TEXT")
    private String challenges;
    
    @Column(columnDefinition = "TEXT")
    private String lessons;
    
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