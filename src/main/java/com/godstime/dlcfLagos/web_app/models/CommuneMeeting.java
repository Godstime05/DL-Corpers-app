package com.godstime.dlcfLagos.web_app.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "commune_meetings")
public class CommuneMeeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "commune_id", nullable = false)
    private CorpersCommune commune;

    @Column(nullable = false)
    private LocalDateTime meetingDate;

    @Column(nullable = false)
    private String venue;

    @Column(nullable = false)
    private String topic;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private int attendanceCount;

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

    public LocalDateTime getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(LocalDateTime meetingDate) {
        this.meetingDate = meetingDate;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAttendanceCount() {
        return attendanceCount;
    }

    public void setAttendanceCount(int attendanceCount) {
        this.attendanceCount = attendanceCount;
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
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
} 