package com.godstime.dlcfLagos.web_app.controller;

import com.godstime.dlcfLagos.web_app.dto.DiscussionDTO;
import com.godstime.dlcfLagos.web_app.model.Discussion;
import com.godstime.dlcfLagos.web_app.model.User;
import com.godstime.dlcfLagos.web_app.service.DiscussionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discussions")
public class DiscussionController {

    @Autowired
    private DiscussionService discussionService;

    @PostMapping
    public ResponseEntity<DiscussionDTO> createDiscussion(
            @Valid @RequestBody DiscussionDTO discussionDTO,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(discussionService.createDiscussion(discussionDTO, currentUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiscussionDTO> updateDiscussion(
            @PathVariable Long id,
            @Valid @RequestBody DiscussionDTO discussionDTO,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(discussionService.updateDiscussion(id, discussionDTO, currentUser));
    }

    @PostMapping("/{id}/close")
    public ResponseEntity<DiscussionDTO> closeDiscussion(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(discussionService.closeDiscussion(id, currentUser));
    }

    @PostMapping("/{id}/archive")
    public ResponseEntity<DiscussionDTO> archiveDiscussion(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(discussionService.archiveDiscussion(id, currentUser));
    }

    @PostMapping("/{id}/pin")
    public ResponseEntity<DiscussionDTO> pinDiscussion(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(discussionService.pinDiscussion(id, currentUser));
    }

    @PostMapping("/{id}/unpin")
    public ResponseEntity<DiscussionDTO> unpinDiscussion(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(discussionService.unpinDiscussion(id, currentUser));
    }

    @PostMapping("/{id}/lock")
    public ResponseEntity<DiscussionDTO> lockDiscussion(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(discussionService.lockDiscussion(id, currentUser));
    }

    @PostMapping("/{id}/unlock")
    public ResponseEntity<DiscussionDTO> unlockDiscussion(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(discussionService.unlockDiscussion(id, currentUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscussion(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser) {
        discussionService.deleteDiscussion(id, currentUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiscussionDTO> getDiscussionById(@PathVariable Long id) {
        return ResponseEntity.ok(discussionService.getDiscussionById(id));
    }

    @GetMapping
    public ResponseEntity<List<DiscussionDTO>> getAllDiscussions() {
        return ResponseEntity.ok(discussionService.getAllDiscussions());
    }

    @GetMapping("/user")
    public ResponseEntity<List<DiscussionDTO>> getDiscussionsByUser(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(discussionService.getDiscussionsByUser(currentUser));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<DiscussionDTO>> getDiscussionsByCategory(
            @PathVariable Discussion.DiscussionCategory category) {
        return ResponseEntity.ok(discussionService.getDiscussionsByCategory(category));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<DiscussionDTO>> getDiscussionsByStatus(
            @PathVariable Discussion.DiscussionStatus status) {
        return ResponseEntity.ok(discussionService.getDiscussionsByStatus(status));
    }

    @GetMapping("/pinned")
    public ResponseEntity<List<DiscussionDTO>> getPinnedDiscussions() {
        return ResponseEntity.ok(discussionService.getPinnedDiscussions());
    }

    @GetMapping("/locked")
    public ResponseEntity<List<DiscussionDTO>> getLockedDiscussions() {
        return ResponseEntity.ok(discussionService.getLockedDiscussions());
    }

    @GetMapping("/tags")
    public ResponseEntity<List<DiscussionDTO>> getDiscussionsByTags(
            @RequestParam List<Long> tagIds) {
        return ResponseEntity.ok(discussionService.getDiscussionsByTags(tagIds));
    }

    @GetMapping("/search")
    public ResponseEntity<List<DiscussionDTO>> searchDiscussions(
            @RequestParam String keyword) {
        return ResponseEntity.ok(discussionService.getDiscussionsByKeyword(keyword));
    }

    @GetMapping("/most-viewed")
    public ResponseEntity<List<DiscussionDTO>> getMostViewedDiscussions(
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(discussionService.getMostViewedDiscussions(limit));
    }

    @GetMapping("/most-commented")
    public ResponseEntity<List<DiscussionDTO>> getMostCommentedDiscussions(
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(discussionService.getMostCommentedDiscussions(limit));
    }

    @PostMapping("/{id}/follow")
    public ResponseEntity<DiscussionDTO> followDiscussion(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(discussionService.followDiscussion(id, currentUser));
    }

    @PostMapping("/{id}/unfollow")
    public ResponseEntity<DiscussionDTO> unfollowDiscussion(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(discussionService.unfollowDiscussion(id, currentUser));
    }

    @PostMapping("/{id}/view")
    public ResponseEntity<DiscussionDTO> incrementViews(@PathVariable Long id) {
        return ResponseEntity.ok(discussionService.incrementViews(id));
    }

    @GetMapping("/stats/category/{category}")
    public ResponseEntity<Long> countDiscussionsByCategory(
            @PathVariable Discussion.DiscussionCategory category) {
        return ResponseEntity.ok(discussionService.countDiscussionsByCategory(category));
    }

    @GetMapping("/stats/status/{status}")
    public ResponseEntity<Long> countDiscussionsByStatus(
            @PathVariable Discussion.DiscussionStatus status) {
        return ResponseEntity.ok(discussionService.countDiscussionsByStatus(status));
    }
} 