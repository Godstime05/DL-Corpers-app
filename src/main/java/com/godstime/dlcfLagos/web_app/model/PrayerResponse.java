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
@Table(name = "prayer_responses")
public class PrayerResponse {
    
    public enum ResponseType {
        PRAYER,
        ENCOURAGEMENT,
        TESTIMONY,
        COUNSEL,
        SCRIPTURE
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prayer_request_id", nullable = false)
    private PrayerRequest prayerRequest;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responded_by", nullable = false)
    private User respondedBy;
    
    @Column(nullable = false, length = 1000)
    private String response;
    
    @Column(nullable = false)
    private LocalDateTime respondedAt;
    
    @Column(nullable = false)
    private boolean isAnonymous;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ResponseType type;
    
    @PrePersist
    protected void onCreate() {
        respondedAt = LocalDateTime.now();
    }
} 