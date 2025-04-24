package com.godstime.dlcfLagos.web_app.service;

import com.godstime.dlcfLagos.web_app.dto.EventDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    
    EventDTO createEvent(EventDTO eventDTO);
    
    EventDTO updateEvent(Long id, EventDTO eventDTO);
    
    void deleteEvent(Long id);
    
    EventDTO getEventById(Long id);
    
    List<EventDTO> getAllEvents();
    
    List<EventDTO> getActiveEvents();
    
    List<EventDTO> getUpcomingEvents();
    
    List<EventDTO> getOngoingEvents();
    
    List<EventDTO> getEventsByCategory(String category);
    
    List<EventDTO> getEventsByStatus(String status);
    
    List<EventDTO> getEventsByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    
    List<EventDTO> getEventsByTargetAudience(String targetAudience, String specificTarget);
    
    List<EventDTO> getEventsByCreator(Long userId);
    
    void toggleEventStatus(Long id);
    
    void updateEventStatus(Long id, String status);
    
    boolean registerForEvent(Long eventId, Long userId);
    
    boolean cancelRegistration(Long eventId, Long userId);
    
    List<EventDTO> getRegisteredEvents(Long userId);
} 