package com.godstime.dlcfLagos.web_app.controllers;

import com.godstime.dlcfLagos.web_app.dto.PrayerRequestDTO;
import com.godstime.dlcfLagos.web_app.models.PrayerRequest;
import com.godstime.dlcfLagos.web_app.models.User;
import com.godstime.dlcfLagos.web_app.services.PrayerRequestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prayer-requests")
public class PrayerRequestController {

    @Autowired
    private PrayerRequestService prayerRequestService;

    @PostMapping
    public ResponseEntity<PrayerRequestDTO> createPrayerRequest(
            @Valid @RequestBody PrayerRequestDTO prayerRequestDTO,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(prayerRequestService.createPrayerRequest(prayerRequestDTO, currentUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrayerRequestDTO> updatePrayerRequest(
            @PathVariable Long id,
            @Valid @RequestBody PrayerRequestDTO prayerRequestDTO,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(prayerRequestService.updatePrayerRequest(id, prayerRequestDTO, currentUser));
    }

    @PostMapping("/{id}/respond")
    public ResponseEntity<PrayerRequestDTO> respondToPrayerRequest(
            @PathVariable Long id,
            @RequestBody String response,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(prayerRequestService.respondToPrayerRequest(id, response, currentUser));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<PrayerRequestDTO> updatePrayerRequestStatus(
            @PathVariable Long id,
            @RequestParam PrayerRequest.PrayerStatus status,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(prayerRequestService.updatePrayerRequestStatus(id, status, currentUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrayerRequest(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser) {
        prayerRequestService.deletePrayerRequest(id, currentUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrayerRequestDTO> getPrayerRequestById(@PathVariable Long id) {
        return ResponseEntity.ok(prayerRequestService.getPrayerRequestById(id));
    }

    @GetMapping
    public ResponseEntity<List<PrayerRequestDTO>> getAllPrayerRequests() {
        return ResponseEntity.ok(prayerRequestService.getAllPrayerRequests());
    }

    @GetMapping("/user")
    public ResponseEntity<List<PrayerRequestDTO>> getPrayerRequestsByUser(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(prayerRequestService.getPrayerRequestsByUser(currentUser));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<PrayerRequestDTO>> getPrayerRequestsByCategory(
            @PathVariable PrayerRequest.PrayerCategory category) {
        return ResponseEntity.ok(prayerRequestService.getPrayerRequestsByCategory(category));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<PrayerRequestDTO>> getPrayerRequestsByStatus(
            @PathVariable PrayerRequest.PrayerStatus status) {
        return ResponseEntity.ok(prayerRequestService.getPrayerRequestsByStatus(status));
    }

    @GetMapping("/anonymous")
    public ResponseEntity<List<PrayerRequestDTO>> getAnonymousPrayerRequests() {
        return ResponseEntity.ok(prayerRequestService.getAnonymousPrayerRequests());
    }

    @GetMapping("/answered")
    public ResponseEntity<List<PrayerRequestDTO>> getAnsweredPrayerRequests() {
        return ResponseEntity.ok(prayerRequestService.getAnsweredPrayerRequests());
    }

    @GetMapping("/unanswered")
    public ResponseEntity<List<PrayerRequestDTO>> getUnansweredPrayerRequests() {
        return ResponseEntity.ok(prayerRequestService.getUnansweredPrayerRequests());
    }

    @GetMapping("/most-prayed")
    public ResponseEntity<List<PrayerRequestDTO>> getMostPrayedForRequests(
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(prayerRequestService.getMostPrayedForRequests(limit));
    }

    @PostMapping("/{id}/pray")
    public ResponseEntity<PrayerRequestDTO> incrementPrayerCount(@PathVariable Long id) {
        return ResponseEntity.ok(prayerRequestService.incrementPrayerCount(id));
    }

    @GetMapping("/stats/status/{status}")
    public ResponseEntity<Long> countPrayerRequestsByStatus(@PathVariable PrayerRequest.PrayerStatus status) {
        return ResponseEntity.ok(prayerRequestService.countPrayerRequestsByStatus(status));
    }

    @GetMapping("/stats/category/{category}")
    public ResponseEntity<Long> countPrayerRequestsByCategory(@PathVariable PrayerRequest.PrayerCategory category) {
        return ResponseEntity.ok(prayerRequestService.countPrayerRequestsByCategory(category));
    }
} 