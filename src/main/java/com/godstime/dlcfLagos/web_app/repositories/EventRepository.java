package com.godstime.dlcfLagos.web_app.repositories;

import com.godstime.dlcfLagos.web_app.models.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    
    List<Event> findByActiveTrue();
    
    List<Event> findByStatus(String status);
    
    List<Event> findByCategory(String category);
    
    List<Event> findByStartDateTimeAfter(LocalDateTime dateTime);
    
    List<Event> findByStartDateTimeBeforeAndEndDateTimeAfter(LocalDateTime start, LocalDateTime end);
    
    List<Event> findByTargetAudience(String targetAudience);
    
    List<Event> findByTargetAudienceAndSpecificTarget(String targetAudience, String specificTarget);
    
    List<Event> findByCreatedById(Long userId);
    
    @Query("SELECT e FROM Event e WHERE e.startDateTime BETWEEN :startDate AND :endDate")
    List<Event> findEventsByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT e FROM Event e JOIN e.registeredUsers u WHERE u.id = :userId")
    List<Event> findEventsByRegisteredUser(@Param("userId") Long userId);
    
    List<Event> findByActiveTrueOrderByStartDateTimeAsc();
    
    Page<Event> findByActiveTrueOrderByStartDateTimeAsc(Pageable pageable);

    List<Event> findByCategoryAndActiveTrueOrderByStartDateTimeAsc(String category);

    List<Event> findByStartDateTimeBetweenAndActiveTrueOrderByStartDateTimeAsc(LocalDateTime startDate, LocalDateTime endDate);

    List<Event> findByCreatedByIdAndActiveTrueOrderByStartDateTimeAsc(Long userId);

    List<Event> findByRegisteredUsersIdAndActiveTrueOrderByStartDateTimeAsc(Long userId);

    List<Event> findByStartDateTimeAfterAndActiveTrueOrderByStartDateTimeAsc(LocalDateTime date);

}