package com.godstime.dlcfLagos.web_app.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "corpers_communes")
public class CorpersCommune {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String localGovernment;

    @Column(nullable = false)
    private String meetingDay;

    @Column(nullable = false)
    private String meetingTime;

    @Column(nullable = false)
    private String meetingFrequency; // Weekly, Bi-weekly, Monthly

    @Column(nullable = false)
    private String communeType; // Lodge-based, Area-based

    @Column(nullable = false)
    private int memberCount;

    @Column(nullable = false)
    private String coordinatorName;

    @Column(nullable = false)
    private String coordinatorPhone;

    @Column(nullable = false)
    private String coordinatorEmail;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String activities;

    @Column(nullable = false)
    private boolean isActive = true;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "commune", cascade = CascadeType.ALL)
    private List<CommuneMeeting> meetings;

    @OneToMany(mappedBy = "commune", cascade = CascadeType.ALL)
    private List<CommuneMember> members;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLocalGovernment() {
        return localGovernment;
    }

    public void setLocalGovernment(String localGovernment) {
        this.localGovernment = localGovernment;
    }

    public String getMeetingDay() {
        return meetingDay;
    }

    public void setMeetingDay(String meetingDay) {
        this.meetingDay = meetingDay;
    }

    public String getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(String meetingTime) {
        this.meetingTime = meetingTime;
    }

    public String getMeetingFrequency() {
        return meetingFrequency;
    }

    public void setMeetingFrequency(String meetingFrequency) {
        this.meetingFrequency = meetingFrequency;
    }

    public String getCommuneType() {
        return communeType;
    }

    public void setCommuneType(String communeType) {
        this.communeType = communeType;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public String getCoordinatorName() {
        return coordinatorName;
    }

    public void setCoordinatorName(String coordinatorName) {
        this.coordinatorName = coordinatorName;
    }

    public String getCoordinatorPhone() {
        return coordinatorPhone;
    }

    public void setCoordinatorPhone(String coordinatorPhone) {
        this.coordinatorPhone = coordinatorPhone;
    }

    public String getCoordinatorEmail() {
        return coordinatorEmail;
    }

    public void setCoordinatorEmail(String coordinatorEmail) {
        this.coordinatorEmail = coordinatorEmail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActivities() {
        return activities;
    }

    public void setActivities(String activities) {
        this.activities = activities;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
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

    public List<CommuneMeeting> getMeetings() {
        return meetings;
    }

    public void setMeetings(List<CommuneMeeting> meetings) {
        this.meetings = meetings;
    }

    public List<CommuneMember> getMembers() {
        return members;
    }

    public void setMembers(List<CommuneMember> members) {
        this.members = members;
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