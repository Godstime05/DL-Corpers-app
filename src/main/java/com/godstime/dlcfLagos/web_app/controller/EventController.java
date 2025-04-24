package com.godstime.dlcfLagos.web_app.controller;

import com.godstime.dlcfLagos.web_app.dto.EventDTO;
import com.godstime.dlcfLagos.web_app.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {
    
    @Autowired
    private EventService eventService;
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<EventDTO> createEvent(@RequestBody EventDTO eventDTO) {
        return ResponseEntity.ok(eventService.createEvent(eventDTO));
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<EventDTO> updateEvent(
            @PathVariable Long id,
            @RequestBody EventDTO eventDTO) {
        return ResponseEntity.ok(eventService.updateEvent(id, eventDTO));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }
    
    @GetMapping
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<EventDTO>> getActiveEvents() {
        return ResponseEntity.ok(eventService.getActiveEvents());
    }
    
    @GetMapping("/upcoming")
    public ResponseEntity<List<EventDTO>> getUpcomingEvents() {
        return ResponseEntity.ok(eventService.getUpcomingEvents());
    }
    
    @GetMapping("/ongoing")
    public ResponseEntity<List<EventDTO>> getOngoingEvents() {
        return ResponseEntity.ok(eventService.getOngoingEvents());
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<List<EventDTO>> getEventsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(eventService.getEventsByCategory(category));
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<EventDTO>> getEventsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(eventService.getEventsByStatus(status));
    }
    
    @GetMapping("/date-range")
    public ResponseEntity<List<EventDTO>> getEventsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(eventService.getEventsByDateRange(startDate, endDate));
    }
    
    @GetMapping("/target")
    public ResponseEntity<List<EventDTO>> getEventsByTargetAudience(
            @RequestParam String targetAudience,
            @RequestParam(required = false) String specificTarget) {
        return ResponseEntity.ok(eventService.getEventsByTargetAudience(targetAudience, specificTarget));
    }
    
    @GetMapping("/creator/{userId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<List<EventDTO>> getEventsByCreator(@PathVariable Long userId) {
        return ResponseEntity.ok(eventService.getEventsByCreator(userId));
    }
    
    @PutMapping("/{id}/toggle")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<Void> toggleEventStatus(@PathVariable Long id) {
        eventService.toggleEventStatus(id);
        return ResponseEntity.ok().build();
    }
    
    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<Void> updateEventStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        eventService.updateEventStatus(id, status);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/{eventId}/register/{userId}")
    public ResponseEntity<Boolean> registerForEvent(
            @PathVariable Long eventId,
            @PathVariable Long userId) {
        return ResponseEntity.ok(eventService.registerForEvent(eventId, userId));
    }
    
    @PostMapping("/{eventId}/cancel/{userId}")
    public ResponseEntity<Boolean> cancelRegistration(
            @PathVariable Long eventId,
            @PathVariable Long userId) {
        return ResponseEntity.ok(eventService.cancelRegistration(eventId, userId));
    }
    
    @GetMapping("/registered/{userId}")
    public ResponseEntity<List<EventDTO>> getRegisteredEvents(@PathVariable Long userId) {
        return ResponseEntity.ok(eventService.getRegisteredEvents(userId));
    }
} 