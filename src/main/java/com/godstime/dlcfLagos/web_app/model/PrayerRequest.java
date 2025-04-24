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
@Table(name = "prayer_requests")
public class PrayerRequest {
    
    public enum PrayerCategory {
        HEALTH,
        FINANCE,
        RELATIONSHIP,
        CAREER,
        SPIRITUAL,
        FAMILY,
        EDUCATION,
        MINISTRY,
        OTHER
    }
    
    public enum PrayerStatus {
        PENDING,
        IN_PROGRESS,
        ANSWERED,
        CLOSED
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 200)
    private String title;
    
    @Column(nullable = false, length = 2000)
    private String content;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submitted_by", nullable = false)
    private User submittedBy;
    
    @Column(nullable = false)
    private LocalDateTime submittedAt;
    
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    @Column(nullable = false)
    private boolean isAnonymous;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PrayerCategory category;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PrayerStatus status;
    
    @Column(nullable = false)
    private int prayerCount;
    
    @Column(length = 1000)
    private String response;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responded_by")
    private User respondedBy;
    
    @Column
    private LocalDateTime respondedAt;
    
    @Column(length = 20)
    private String priority; // HIGH, MEDIUM, LOW
    
    @Column(length = 50)
    private String targetAudience; // ALL, SPECIFIC_ZONE, SPECIFIC_LGA, SPECIFIC_CDS
    
    @Column(length = 100)
    private String specificTarget; // Zone name, LGA name, or CDS group name if targetAudience is specific
    
    @Column(nullable = false)
    private boolean allowComments;
    
    @Column(nullable = false)
    private boolean allowPrayerPartners;
    
    @Column(nullable = false)
    private Integer prayerPartnerCount;
    
    @OneToMany(mappedBy = "prayerRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PrayerResponse> responses = new HashSet<>();
    
    @ManyToMany
    @JoinTable(
        name = "prayer_request_prayer_partners",
        joinColumns = @JoinColumn(name = "prayer_request_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> prayerPartners = new HashSet<>();
    
    @PrePersist
    protected void onCreate() {
        submittedAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        status = PrayerStatus.PENDING;
        prayerCount = 0;
        prayerPartnerCount = 0;
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
} 