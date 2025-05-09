package com.godstime.dlcfLagos.web_app.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

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

    @NotBlank(message = "Location is required")
    @Column(nullable = false)
    private String location;

    @NotNull(message = "Date is required")
    @Column(name = "evangelism_date", nullable = false)
    private LocalDateTime evangelismDate;

    @Column(name = "number_of_souls_won")
    private Integer numberOfSoulsWon;

    @Column(name = "number_of_follow_ups")
    private Integer numberOfFollowUps;

    @Column(columnDefinition = "TEXT")
    private String report;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fellowship_center_id", nullable = false)
    private FellowshipCenter fellowshipCenter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id", nullable = false)
    private User createdBy;

    @ManyToMany
    @JoinTable(
        name = "evangelism_team_members",
        joinColumns = @JoinColumn(name = "evangelism_record_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> teamMembers = new HashSet<>();

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