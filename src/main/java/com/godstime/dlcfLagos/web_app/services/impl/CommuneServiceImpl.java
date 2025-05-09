package com.godstime.dlcfLagos.web_app.services.impl;

import com.godstime.dlcfLagos.web_app.models.CorpersCommune;
import com.godstime.dlcfLagos.web_app.models.CommuneMember;
import com.godstime.dlcfLagos.web_app.models.CommuneMeeting;
import com.godstime.dlcfLagos.web_app.repositories.CommuneRepository;
import com.godstime.dlcfLagos.web_app.repositories.CommuneMemberRepository;
import com.godstime.dlcfLagos.web_app.repositories.CommuneMeetingRepository;
import com.godstime.dlcfLagos.web_app.services.CommuneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommuneServiceImpl implements CommuneService {

    @Autowired
    private CommuneRepository communeRepository;

    @Autowired
    private CommuneMemberRepository memberRepository;

    @Autowired
    private CommuneMeetingRepository meetingRepository;

    @Override
    public List<CorpersCommune> getAllCommunes() {
        return communeRepository.findAll();
    }

    @Override
    public Optional<CorpersCommune> getCommuneById(Long id) {
        return communeRepository.findById(id);
    }

    @Override
    @Transactional
    public CorpersCommune createCommune(CorpersCommune commune) {
        return communeRepository.save(commune);
    }

    @Override
    @Transactional
    public CorpersCommune updateCommune(Long id, CorpersCommune commune) {
        if (!communeRepository.existsById(id)) {
            throw new RuntimeException("Commune not found");
        }
        commune.setId(id);
        return communeRepository.save(commune);
    }

    @Override
    @Transactional
    public void deleteCommune(Long id) {
        communeRepository.deleteById(id);
    }

    @Override
    public List<CommuneMember> getCommuneMembers(Long communeId) {
        return memberRepository.findByCommuneId(communeId);
    }

    @Override
    @Transactional
    public CommuneMember addMember(Long communeId, CommuneMember member) {
        CorpersCommune commune = communeRepository.findById(communeId)
            .orElseThrow(() -> new RuntimeException("Commune not found"));
        member.setCommune(commune);
        return memberRepository.save(member);
    }

    @Override
    @Transactional
    public CommuneMember updateMember(Long communeId, Long memberId, CommuneMember member) {
        if (!memberRepository.existsByIdAndCommuneId(memberId, communeId)) {
            throw new RuntimeException("Member not found in commune");
        }
        member.setId(memberId);
        return memberRepository.save(member);
    }

    @Override
    @Transactional
    public void removeMember(Long communeId, Long memberId) {
        if (!memberRepository.existsByIdAndCommuneId(memberId, communeId)) {
            throw new RuntimeException("Member not found in commune");
        }
        memberRepository.deleteById(memberId);
    }

    @Override
    public List<CommuneMeeting> getCommuneMeetings(Long communeId) {
        return meetingRepository.findByCommuneId(communeId);
    }

    @Override
    @Transactional
    public CommuneMeeting createMeeting(Long communeId, CommuneMeeting meeting) {
        CorpersCommune commune = communeRepository.findById(communeId)
            .orElseThrow(() -> new RuntimeException("Commune not found"));
        meeting.setCommune(commune);
        return meetingRepository.save(meeting);
    }

    @Override
    @Transactional
    public CommuneMeeting updateMeeting(Long communeId, Long meetingId, CommuneMeeting meeting) {
        if (!meetingRepository.existsByIdAndCommuneId(meetingId, communeId)) {
            throw new RuntimeException("Meeting not found in commune");
        }
        meeting.setId(meetingId);
        return meetingRepository.save(meeting);
    }

    @Override
    @Transactional
    public void deleteMeeting(Long communeId, Long meetingId) {
        if (!meetingRepository.existsByIdAndCommuneId(meetingId, communeId)) {
            throw new RuntimeException("Meeting not found in commune");
        }
        meetingRepository.deleteById(meetingId);
    }

    @Override
    public List<CorpersCommune> searchCommunes(String keyword) {
        return communeRepository.findByNameContainingIgnoreCaseOrLocationContainingIgnoreCase(keyword, keyword);
    }

    @Override
    public List<CommuneMember> searchMembers(Long communeId, String keyword) {
        return memberRepository.findByCommuneIdAndNameContainingIgnoreCase(communeId, keyword);
    }

    @Override
    public List<CommuneMeeting> getUpcomingMeetings(Long communeId) {
        return meetingRepository.findByCommuneIdAndMeetingDateAfter(communeId, LocalDateTime.now());
    }
} 