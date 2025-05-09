package com.godstime.dlcfLagos.web_app.controllers;

import com.godstime.dlcfLagos.web_app.dto.MessageResponseDTO;
import com.godstime.dlcfLagos.web_app.dto.PrayerResponseDTO;
import com.godstime.dlcfLagos.web_app.models.PrayerResponse;
import com.godstime.dlcfLagos.web_app.services.PrayerResponseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/prayer-responses")
@Tag(name = "Prayer Response Controller", description = "APIs for managing prayer responses")
public class PrayerResponseController {

    private final PrayerResponseService prayerResponseService;

    public PrayerResponseController(PrayerResponseService prayerResponseService) {
        this.prayerResponseService = prayerResponseService;
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Create a new prayer response")
    public ResponseEntity<PrayerResponseDTO> createResponse(@Valid @RequestBody PrayerResponseDTO responseDTO) {
        PrayerResponseDTO createdResponse = prayerResponseService.createResponse(responseDTO);
        return ResponseEntity.ok(createdResponse);
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Update an existing prayer response")
    public ResponseEntity<PrayerResponseDTO> updateResponse(
            @PathVariable Long id,
            @Valid @RequestBody PrayerResponseDTO responseDTO) {
        PrayerResponseDTO updatedResponse = prayerResponseService.updateResponse(id, responseDTO);
        return ResponseEntity.ok(updatedResponse);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Delete a prayer response")
    public ResponseEntity<MessageResponseDTO> deleteResponse(@PathVariable Long id) {
        prayerResponseService.deleteResponse(id);
        return ResponseEntity.ok(MessageResponseDTO.of("Prayer response deleted successfully"));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a specific prayer response by ID")
    public ResponseEntity<PrayerResponseDTO> getResponseById(@PathVariable Long id) {
        PrayerResponseDTO response = prayerResponseService.getResponseById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/prayer-request/{prayerRequestId}")
    @Operation(summary = "Get all responses for a prayer request with pagination")
    public ResponseEntity<Page<PrayerResponseDTO>> getResponsesByPrayerRequest(
            @PathVariable Long prayerRequestId,
            @PageableDefault(size = 10) Pageable pageable,
            @RequestParam(required = false) PrayerResponse.ResponseType type,
            @RequestParam(required = false) Boolean isAnonymous,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate) {
        Page<PrayerResponseDTO> responses = prayerResponseService.getResponsesByPrayerRequest(
                prayerRequestId, pageable, type, isAnonymous, startDate, endDate);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get all responses by a specific user")
    public ResponseEntity<Page<PrayerResponseDTO>> getResponsesByUser(
            @PathVariable Long userId,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<PrayerResponseDTO> responses = prayerResponseService.getResponsesByUser(userId, pageable);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/prayer-request/{prayerRequestId}/non-anonymous")
    @Operation(summary = "Get non-anonymous responses for a prayer request")
    public ResponseEntity<Page<PrayerResponseDTO>> getNonAnonymousResponses(
            @PathVariable Long prayerRequestId,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<PrayerResponseDTO> responses = prayerResponseService.getNonAnonymousResponses(prayerRequestId, pageable);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/prayer-request/{prayerRequestId}/count")
    @Operation(summary = "Count responses for a prayer request")
    public ResponseEntity<Long> countResponses(@PathVariable Long prayerRequestId) {
        long count = prayerResponseService.countResponses(prayerRequestId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/recent")
    @Operation(summary = "Get recent prayer responses")
    public ResponseEntity<Page<PrayerResponseDTO>> getRecentResponses(
            @PageableDefault(size = 10) Pageable pageable,
            @RequestParam(required = false) PrayerResponse.ResponseType type) {
        Page<PrayerResponseDTO> responses = prayerResponseService.getRecentResponses(pageable, type);
        return ResponseEntity.ok(responses);
    }
} 