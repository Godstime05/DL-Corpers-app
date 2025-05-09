package com.godstime.dlcfLagos.web_app.repositories;

import com.godstime.dlcfLagos.web_app.models.CommuneMeeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommuneMeetingRepository extends JpaRepository<CommuneMeeting, Long> {
    List<CommuneMeeting> findByCommuneId(Long communeId);
    List<CommuneMeeting> findByCommuneIdAndMeetingDateAfter(Long communeId, LocalDateTime date);
    boolean existsByIdAndCommuneId(Long meetingId, Long communeId);
} 