package com.godstime.dlcfLagos.web_app.controllers;

import com.godstime.dlcfLagos.web_app.dto.AnnouncementDTO;
import com.godstime.dlcfLagos.web_app.services.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {
    
    @Autowired
    private AnnouncementService announcementService;
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<AnnouncementDTO> createAnnouncement(@RequestBody AnnouncementDTO announcementDTO) {
        return ResponseEntity.ok(announcementService.createAnnouncement(announcementDTO));
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<AnnouncementDTO> updateAnnouncement(
            @PathVariable Long id,
            @RequestBody AnnouncementDTO announcementDTO) {
        return ResponseEntity.ok(announcementService.updateAnnouncement(id, announcementDTO));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<Void> deleteAnnouncement(@PathVariable Long id) {
        announcementService.deleteAnnouncement(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AnnouncementDTO> getAnnouncementById(@PathVariable Long id) {
        return ResponseEntity.ok(announcementService.getAnnouncementById(id));
    }
    
    @GetMapping
    public ResponseEntity<List<AnnouncementDTO>> getAllAnnouncements() {
        return ResponseEntity.ok(announcementService.getAllAnnouncements());
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<AnnouncementDTO>> getActiveAnnouncements() {
        return ResponseEntity.ok(announcementService.getActiveAnnouncements());
    }
    
    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<AnnouncementDTO>> getAnnouncementsByPriority(@PathVariable String priority) {
        return ResponseEntity.ok(announcementService.getAnnouncementsByPriority(priority));
    }
    
    @GetMapping("/target")
    public ResponseEntity<List<AnnouncementDTO>> getAnnouncementsByTargetAudience(
            @RequestParam String targetAudience,
            @RequestParam(required = false) String specificTarget) {
        return ResponseEntity.ok(announcementService.getAnnouncementsByTargetAudience(targetAudience, specificTarget));
    }
    
    @GetMapping("/creator/{userId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<List<AnnouncementDTO>> getAnnouncementsByCreator(@PathVariable Long userId) {
        return ResponseEntity.ok(announcementService.getAnnouncementsByCreator(userId));
    }
    
    @PutMapping("/{id}/toggle")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<Void> toggleAnnouncementStatus(@PathVariable Long id) {
        announcementService.toggleAnnouncementStatus(id);
        return ResponseEntity.ok().build();
    }
} 