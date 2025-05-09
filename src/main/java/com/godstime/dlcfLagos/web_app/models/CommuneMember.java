package com.godstime.dlcfLagos.web_app.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "commune_members")
public class CommuneMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "commune_id", nullable = false)
    private CorpersCommune commune;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String stateCode;

    @Column(nullable = false)
    private String placeOfPrimaryAssignment;

    @Column(nullable = false)
    private String role; // e.g., "Member", "Coordinator", "Assistant Coordinator"

    @Column(nullable = false)
    private LocalDateTime joinedDate;

    @Column(nullable = false)
    private boolean isActive;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CorpersCommune getCommune() {
        return commune;
    }

    public void setCommune(CorpersCommune commune) {
        this.commune = commune;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getPlaceOfPrimaryAssignment() {
        return placeOfPrimaryAssignment;
    }

    public void setPlaceOfPrimaryAssignment(String placeOfPrimaryAssignment) {
        this.placeOfPrimaryAssignment = placeOfPrimaryAssignment;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(LocalDateTime joinedDate) {
        this.joinedDate = joinedDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (joinedDate == null) {
            joinedDate = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
} 