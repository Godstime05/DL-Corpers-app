package com.godstime.dlcfLagos.web_app.controllers;

import com.godstime.dlcfLagos.web_app.models.CorpersCommune;
import com.godstime.dlcfLagos.web_app.models.CommuneMember;
import com.godstime.dlcfLagos.web_app.models.CommuneMeeting;
import com.godstime.dlcfLagos.web_app.services.CommuneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/communes")
public class CommuneController {

    @Autowired
    private CommuneService communeService;

    // Commune endpoints
    @GetMapping
    public ResponseEntity<List<CorpersCommune>> getAllCommunes() {
        return ResponseEntity.ok(communeService.getAllCommunes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CorpersCommune> getCommuneById(@PathVariable Long id) {
        return communeService.getCommuneById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CorpersCommune> createCommune(@RequestBody CorpersCommune commune) {
        return ResponseEntity.ok(communeService.createCommune(commune));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CorpersCommune> updateCommune(@PathVariable Long id, @RequestBody CorpersCommune commune) {
        return ResponseEntity.ok(communeService.updateCommune(id, commune));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommune(@PathVariable Long id) {
        communeService.deleteCommune(id);
        return ResponseEntity.ok().build();
    }

    // Member endpoints
    @GetMapping("/{communeId}/members")
    public ResponseEntity<List<CommuneMember>> getCommuneMembers(@PathVariable Long communeId) {
        return ResponseEntity.ok(communeService.getCommuneMembers(communeId));
    }

    @PostMapping("/{communeId}/members")
    public ResponseEntity<CommuneMember> addMember(@PathVariable Long communeId, @RequestBody CommuneMember member) {
        return ResponseEntity.ok(communeService.addMember(communeId, member));
    }

    @PutMapping("/{communeId}/members/{memberId}")
    public ResponseEntity<CommuneMember> updateMember(
            @PathVariable Long communeId,
            @PathVariable Long memberId,
            @RequestBody CommuneMember member) {
        return ResponseEntity.ok(communeService.updateMember(communeId, memberId, member));
    }

    @DeleteMapping("/{communeId}/members/{memberId}")
    public ResponseEntity<Void> removeMember(@PathVariable Long communeId, @PathVariable Long memberId) {
        communeService.removeMember(communeId, memberId);
        return ResponseEntity.ok().build();
    }

    // Meeting endpoints
    @GetMapping("/{communeId}/meetings")
    public ResponseEntity<List<CommuneMeeting>> getCommuneMeetings(@PathVariable Long communeId) {
        return ResponseEntity.ok(communeService.getCommuneMeetings(communeId));
    }

    @PostMapping("/{communeId}/meetings")
    public ResponseEntity<CommuneMeeting> createMeeting(
            @PathVariable Long communeId,
            @RequestBody CommuneMeeting meeting) {
        return ResponseEntity.ok(communeService.createMeeting(communeId, meeting));
    }

    @PutMapping("/{communeId}/meetings/{meetingId}")
    public ResponseEntity<CommuneMeeting> updateMeeting(
            @PathVariable Long communeId,
            @PathVariable Long meetingId,
            @RequestBody CommuneMeeting meeting) {
        return ResponseEntity.ok(communeService.updateMeeting(communeId, meetingId, meeting));
    }

    @DeleteMapping("/{communeId}/meetings/{meetingId}")
    public ResponseEntity<Void> deleteMeeting(@PathVariable Long communeId, @PathVariable Long meetingId) {
        communeService.deleteMeeting(communeId, meetingId);
        return ResponseEntity.ok().build();
    }

    // Search endpoints
    @GetMapping("/search")
    public ResponseEntity<List<CorpersCommune>> searchCommunes(@RequestParam String keyword) {
        return ResponseEntity.ok(communeService.searchCommunes(keyword));
    }

    @GetMapping("/{communeId}/members/search")
    public ResponseEntity<List<CommuneMember>> searchMembers(
            @PathVariable Long communeId,
            @RequestParam String keyword) {
        return ResponseEntity.ok(communeService.searchMembers(communeId, keyword));
    }

    @GetMapping("/{communeId}/meetings/upcoming")
    public ResponseEntity<List<CommuneMeeting>> getUpcomingMeetings(@PathVariable Long communeId) {
        return ResponseEntity.ok(communeService.getUpcomingMeetings(communeId));
    }
} 