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
@Table(name = "testimonies")
public class Testimony {
    
    public enum TestimonyCategory {
        HEALING,
        DELIVERANCE,
        FINANCIAL_BREAKTHROUGH,
        MARRIAGE,
        CAREER,
        ACADEMIC,
        MINISTRY,
        FAMILY,
        OTHER
    }
    
    public enum TestimonyStatus {
        PENDING,
        APPROVED,
        REJECTED
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 200)
    private String title;
    
    @Column(nullable = false, length = 5000)
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
    private TestimonyCategory category;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TestimonyStatus status;
    
    @Column(length = 1000)
    private String rejectionReason;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by")
    private User approvedBy;
    
    @Column
    private LocalDateTime approvedAt;
    
    @Column(nullable = false)
    private int likesCount;
    
    @Column(nullable = false)
    private int sharesCount;
    
    @Column(length = 100)
    private String location;
    
    @Column(length = 100)
    private String eventDate;
    
    @OneToMany(mappedBy = "testimony", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TestimonyComment> comments = new HashSet<>();
    
    @ManyToMany
    @JoinTable(
        name = "testimony_likes",
        joinColumns = @JoinColumn(name = "testimony_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> likedBy = new HashSet<>();
    
    @PrePersist
    protected void onCreate() {
        submittedAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        status = TestimonyStatus.PENDING;
        likesCount = 0;
        sharesCount = 0;
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
} 