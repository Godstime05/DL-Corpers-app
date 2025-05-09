package com.godstime.dlcfLagos.web_app.models;

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
@Table(name = "discussions")
public class Discussion {
    
    public enum DiscussionCategory {
        BIBLE_STUDY,
        PRAYER,
        EVANGELISM,
        FELLOWSHIP,
        MINISTRY,
        GENERAL
    }
    
    public enum DiscussionStatus {
        ACTIVE,
        CLOSED,
        ARCHIVED
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 200)
    private String title;
    
    @Column(nullable = false, length = 5000)
    private String content;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiscussionCategory category;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiscussionStatus status;
    
    @Column(nullable = false)
    private int viewsCount;
    
    @Column(nullable = false)
    private int commentsCount;
    
    @Column(nullable = false)
    private boolean isPinned;
    
    @Column(nullable = false)
    private boolean isLocked;
    
    @ManyToMany
    @JoinTable(
        name = "discussion_tags",
        joinColumns = @JoinColumn(name = "discussion_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();
    
    @OneToMany(mappedBy = "discussion", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();
    
    @ManyToMany
    @JoinTable(
        name = "discussion_followers",
        joinColumns = @JoinColumn(name = "discussion_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> followers = new HashSet<>();
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        status = DiscussionStatus.ACTIVE;
        viewsCount = 0;
        commentsCount = 0;
        isPinned = false;
        isLocked = false;
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
} 