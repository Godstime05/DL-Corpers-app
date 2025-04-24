package com.godstime.dlcfLagos.web_app.service.impl;

import com.godstime.dlcfLagos.web_app.dto.EventDTO;
import com.godstime.dlcfLagos.web_app.model.Event;
import com.godstime.dlcfLagos.web_app.model.User;
import com.godstime.dlcfLagos.web_app.repository.EventRepository;
import com.godstime.dlcfLagos.web_app.service.EventService;
import com.godstime.dlcfLagos.web_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EventServiceImpl implements EventService {
    
    @Autowired
    private EventRepository eventRepository;
    
    @Autowired
    private UserService userService;
    
    @Override
    public EventDTO createEvent(EventDTO eventDTO) {
        User currentUser = userService.getCurrentUser();
        
        Event event = new Event();
        event.setTitle(eventDTO.getTitle());
        event.setDescription(eventDTO.getDescription());
        event.setStartDateTime(eventDTO.getStartDateTime());
        event.setEndDateTime(eventDTO.getEndDateTime());
        event.setLocation(eventDTO.getLocation());
        event.setCreatedBy(currentUser);
        event.setCategory(eventDTO.getCategory());
        event.setMaxParticipants(eventDTO.getMaxParticipants());
        event.setTargetAudience(eventDTO.getTargetAudience());
        event.setSpecificTarget(eventDTO.getSpecificTarget());
        event.setRegistrationLink(eventDTO.getRegistrationLink());
        event.setImageUrl(eventDTO.getImageUrl());
        
        Event savedEvent = eventRepository.save(event);
        return convertToDTO(savedEvent);
    }
    
    @Override
    public EventDTO updateEvent(Long id, EventDTO eventDTO) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: Event not found."));
        
        event.setTitle(eventDTO.getTitle());
        event.setDescription(eventDTO.getDescription());
        event.setStartDateTime(eventDTO.getStartDateTime());
        event.setEndDateTime(eventDTO.getEndDateTime());
        event.setLocation(eventDTO.getLocation());
        event.setCategory(eventDTO.getCategory());
        event.setMaxParticipants(eventDTO.getMaxParticipants());
        event.setTargetAudience(eventDTO.getTargetAudience());
        event.setSpecificTarget(eventDTO.getSpecificTarget());
        event.setRegistrationLink(eventDTO.getRegistrationLink());
        event.setImageUrl(eventDTO.getImageUrl());
        
        Event updatedEvent = eventRepository.save(event);
        return convertToDTO(updatedEvent);
    }
    
    @Override
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
    
    @Override
    public EventDTO getEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: Event not found."));
        return convertToDTO(event);
    }
    
    @Override
    public List<EventDTO> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<EventDTO> getActiveEvents() {
        return eventRepository.findByActiveTrue().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<EventDTO> getUpcomingEvents() {
        return eventRepository.findByStartDateTimeAfter(LocalDateTime.now()).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<EventDTO> getOngoingEvents() {
        return eventRepository.findByStartDateTimeBeforeAndEndDateTimeAfter(
                LocalDateTime.now(), LocalDateTime.now()).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<EventDTO> getEventsByCategory(String category) {
        return eventRepository.findByCategory(category).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<EventDTO> getEventsByStatus(String status) {
        return eventRepository.findByStatus(status).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<EventDTO> getEventsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return eventRepository.findEventsByDateRange(startDate, endDate).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<EventDTO> getEventsByTargetAudience(String targetAudience, String specificTarget) {
        if (specificTarget != null && !specificTarget.isEmpty()) {
            return eventRepository.findByTargetAudienceAndSpecificTarget(targetAudience, specificTarget).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
        return eventRepository.findByTargetAudience(targetAudience).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<EventDTO> getEventsByCreator(Long userId) {
        return eventRepository.findByCreatedById(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public void toggleEventStatus(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: Event not found."));
        event.setActive(!event.isActive());
        eventRepository.save(event);
    }
    
    @Override
    public void updateEventStatus(Long id, String status) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: Event not found."));
        event.setStatus(status);
        eventRepository.save(event);
    }
    
    @Override
    public boolean registerForEvent(Long eventId, Long userId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Error: Event not found."));
        User user = userService.findUserById(userId);
        
        if (event.getMaxParticipants() != null && 
            event.getRegisteredUsers().size() >= event.getMaxParticipants()) {
            return false;
        }
        event.getRegisteredUsers().add(user);
        eventRepository.save(event);
        return true;
    }
    @Override
    public boolean cancelRegistration(Long eventId, Long userId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Error: Event not found."));
        User user = userService.findUserById(userId);
        
        if (!event.getRegisteredUsers().contains(user)) {
            return false;
        }
        
        event.getRegisteredUsers().remove(user);
        eventRepository.save(event);
        return true;
    }
    
    @Override
    public List<EventDTO> getRegisteredEvents(Long userId) {
        return eventRepository.findEventsByRegisteredUser(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    private EventDTO convertToDTO(Event event) {
        EventDTO dto = new EventDTO();
        dto.setId(event.getId());
        dto.setTitle(event.getTitle());
        dto.setDescription(event.getDescription());
        dto.setStartDateTime(event.getStartDateTime());
        dto.setEndDateTime(event.getEndDateTime());
        dto.setLocation(event.getLocation());
        dto.setCreatedBy(event.getCreatedBy().getId());
        dto.setCreatedAt(event.getCreatedAt());
        dto.setUpdatedAt(event.getUpdatedAt());
        dto.setActive(event.isActive());
        dto.setCategory(event.getCategory());
        dto.setStatus(event.getStatus());
        dto.setMaxParticipants(event.getMaxParticipants());
        dto.setTargetAudience(event.getTargetAudience());
        dto.setSpecificTarget(event.getSpecificTarget());
        dto.setRegistrationLink(event.getRegistrationLink());
        dto.setImageUrl(event.getImageUrl());
        return dto;
    }
} 