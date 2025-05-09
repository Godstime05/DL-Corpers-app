package com.godstime.dlcfLagos.web_app.services;

import com.godstime.dlcfLagos.web_app.models.CorpersCommune;
import com.godstime.dlcfLagos.web_app.models.CommuneMember;
import com.godstime.dlcfLagos.web_app.models.CommuneMeeting;
import java.util.List;
import java.util.Optional;

public interface CommuneService {
    // Commune operations
    List<CorpersCommune> getAllCommunes();
    Optional<CorpersCommune> getCommuneById(Long id);
    CorpersCommune createCommune(CorpersCommune commune);
    CorpersCommune updateCommune(Long id, CorpersCommune commune);
    void deleteCommune(Long id);
    
    // Member operations
    List<CommuneMember> getCommuneMembers(Long communeId);
    CommuneMember addMember(Long communeId, CommuneMember member);
    CommuneMember updateMember(Long communeId, Long memberId, CommuneMember member);
    void removeMember(Long communeId, Long memberId);
    
    // Meeting operations
    List<CommuneMeeting> getCommuneMeetings(Long communeId);
    CommuneMeeting createMeeting(Long communeId, CommuneMeeting meeting);
    CommuneMeeting updateMeeting(Long communeId, Long meetingId, CommuneMeeting meeting);
    void deleteMeeting(Long communeId, Long meetingId);
    
    // Search and filter operations
    List<CorpersCommune> searchCommunes(String keyword);
    List<CommuneMember> searchMembers(Long communeId, String keyword);
    List<CommuneMeeting> getUpcomingMeetings(Long communeId);
} 