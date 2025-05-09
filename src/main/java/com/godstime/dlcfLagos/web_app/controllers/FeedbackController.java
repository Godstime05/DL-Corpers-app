package com.godstime.dlcfLagos.web_app.controllers;

import com.godstime.dlcfLagos.web_app.dto.FeedbackDTO;
import com.godstime.dlcfLagos.web_app.models.Feedback;
import com.godstime.dlcfLagos.web_app.models.User;
import com.godstime.dlcfLagos.web_app.services.FeedbackService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<FeedbackDTO> createFeedback(
            @Valid @RequestBody FeedbackDTO feedbackDTO,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(feedbackService.createFeedback(feedbackDTO, currentUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FeedbackDTO> updateFeedback(
            @PathVariable Long id,
            @Valid @RequestBody FeedbackDTO feedbackDTO,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(feedbackService.updateFeedback(id, feedbackDTO, currentUser));
    }

    @PostMapping("/{id}/respond")
    public ResponseEntity<FeedbackDTO> respondToFeedback(
            @PathVariable Long id,
            @RequestBody String response,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(feedbackService.respondToFeedback(id, response, currentUser));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<FeedbackDTO> updateFeedbackStatus(
            @PathVariable Long id,
            @RequestParam Feedback.FeedbackStatus status,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(feedbackService.updateFeedbackStatus(id, status, currentUser));
    }

    @PutMapping("/{id}/priority")
    public ResponseEntity<FeedbackDTO> updateFeedbackPriority(
            @PathVariable Long id,
            @RequestParam Feedback.FeedbackPriority priority,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(feedbackService.updateFeedbackPriority(id, priority, currentUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedback(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser) {
        feedbackService.deleteFeedback(id, currentUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedbackDTO> getFeedbackById(@PathVariable Long id) {
        return ResponseEntity.ok(feedbackService.getFeedbackById(id));
    }

    @GetMapping
    public ResponseEntity<List<FeedbackDTO>> getAllFeedback() {
        return ResponseEntity.ok(feedbackService.getAllFeedback());
    }

    @GetMapping("/user")
    public ResponseEntity<List<FeedbackDTO>> getFeedbackByUser(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(feedbackService.getFeedbackByUser(currentUser));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<FeedbackDTO>> getFeedbackByType(@PathVariable Feedback.FeedbackType type) {
        return ResponseEntity.ok(feedbackService.getFeedbackByType(type));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<FeedbackDTO>> getFeedbackByStatus(@PathVariable Feedback.FeedbackStatus status) {
        return ResponseEntity.ok(feedbackService.getFeedbackByStatus(status));
    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<FeedbackDTO>> getFeedbackByPriority(@PathVariable Feedback.FeedbackPriority priority) {
        return ResponseEntity.ok(feedbackService.getFeedbackByPriority(priority));
    }

    @GetMapping("/anonymous")
    public ResponseEntity<List<FeedbackDTO>> getAnonymousFeedback() {
        return ResponseEntity.ok(feedbackService.getAnonymousFeedback());
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<FeedbackDTO>> getFeedbackByDateRange(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return ResponseEntity.ok(feedbackService.getFeedbackByDateRange(startDate, endDate));
    }

    @GetMapping("/stats/status/{status}")
    public ResponseEntity<Long> countFeedbackByStatus(@PathVariable Feedback.FeedbackStatus status) {
        return ResponseEntity.ok(feedbackService.countFeedbackByStatus(status));
    }

    @GetMapping("/stats/type/{type}")
    public ResponseEntity<Long> countFeedbackByType(@PathVariable Feedback.FeedbackType type) {
        return ResponseEntity.ok(feedbackService.countFeedbackByType(type));
    }

    @GetMapping("/stats/priority/{priority}")
    public ResponseEntity<Long> countFeedbackByPriority(@PathVariable Feedback.FeedbackPriority priority) {
        return ResponseEntity.ok(feedbackService.countFeedbackByPriority(priority));
    }
} 