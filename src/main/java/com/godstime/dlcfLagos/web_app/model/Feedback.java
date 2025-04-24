package com.godstime.dlcfLagos.web_app.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "feedbacks")
public class Feedback {
    
    public enum FeedbackType {
        SUGGESTION,
        COMPLAINT,
        APPRECIATION,
        QUESTION,
        REPORT
    }
    
    public enum FeedbackStatus {
        PENDING,
        IN_REVIEW,
        RESOLVED,
        CLOSED
    }
    
    public enum FeedbackPriority {
        LOW,
        MEDIUM,
        HIGH,
        URGENT
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
    private FeedbackType type;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FeedbackStatus status;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FeedbackPriority priority;
    
    @Column(length = 1000)
    private String response;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responded_by")
    private User respondedBy;
    
    @Column
    private LocalDateTime respondedAt;
    
    @Column(length = 500)
    private String resolutionNotes;
    
    @PrePersist
    protected void onCreate() {
        submittedAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        status = FeedbackStatus.PENDING;
        priority = FeedbackPriority.MEDIUM;
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
} 