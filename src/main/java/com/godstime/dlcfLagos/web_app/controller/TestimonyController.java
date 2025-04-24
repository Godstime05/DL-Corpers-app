package com.godstime.dlcfLagos.web_app.controller;

import com.godstime.dlcfLagos.web_app.dto.MessageResponseDTO;
import com.godstime.dlcfLagos.web_app.dto.TestimonyDTO;
import com.godstime.dlcfLagos.web_app.exception.ResourceNotFoundException;
import com.godstime.dlcfLagos.web_app.exception.UnauthorizedException;
import com.godstime.dlcfLagos.web_app.model.Testimony;
import com.godstime.dlcfLagos.web_app.model.User;
import com.godstime.dlcfLagos.web_app.service.TestimonyService;
import com.godstime.dlcfLagos.web_app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/testimonies")
@Tag(name = "Testimony Controller", description = "APIs for managing testimonies")
public class TestimonyController {

    private final TestimonyService testimonyService;
    private final UserService userService;

    public TestimonyController(TestimonyService testimonyService, UserService userService) {
        this.testimonyService = testimonyService;
        this.userService = userService;
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Create a new testimony")
    public ResponseEntity<?> createTestimony(
            @Valid @RequestBody TestimonyDTO testimonyDTO,
            Authentication authentication) {
        try {
            User user = userService.findUserByEmail(authentication.getName());
            TestimonyDTO createdTestimony = testimonyService.createTestimony(testimonyDTO, user);
            return ResponseEntity.ok(createdTestimony);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponseDTO.of("Error creating testimony: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Update an existing testimony")
    public ResponseEntity<?> updateTestimony(
            @PathVariable Long id,
            @Valid @RequestBody TestimonyDTO testimonyDTO,
            Authentication authentication) {
        try {
            User user = userService.findUserByEmail(authentication.getName());
            TestimonyDTO updatedTestimony = testimonyService.updateTestimony(id, testimonyDTO, user);
            return ResponseEntity.ok(updatedTestimony);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(MessageResponseDTO.of(e.getMessage()));
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(MessageResponseDTO.of(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponseDTO.of("Error updating testimony: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Delete a testimony")
    public ResponseEntity<?> deleteTestimony(
            @PathVariable Long id,
            Authentication authentication) {
        try {
            User user = userService.findUserByEmail(authentication.getName());
            testimonyService.deleteTestimony(id, user);
            return ResponseEntity.ok(MessageResponseDTO.of("Testimony deleted successfully"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(MessageResponseDTO.of(e.getMessage()));
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(MessageResponseDTO.of(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponseDTO.of("Error deleting testimony: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a specific testimony by ID")
    public ResponseEntity<?> getTestimonyById(@PathVariable Long id) {
        try {
            TestimonyDTO testimony = testimonyService.getTestimonyById(id);
            return ResponseEntity.ok(testimony);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(MessageResponseDTO.of(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponseDTO.of("Error retrieving testimony: " + e.getMessage()));
        }
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get testimonies by status")
    public ResponseEntity<?> getTestimoniesByStatus(
            @PathVariable Testimony.TestimonyStatus status,
            @PageableDefault(size = 10) Pageable pageable) {
        try {
//            List<TestimonyDTO> testimonies = testimonyService.getTestimoniesByStatus(status);
            Page<TestimonyDTO> testimonies = testimonyService.getTestimoniesByStatus(status, pageable);
            return ResponseEntity.ok(testimonies);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponseDTO.of("Error retrieving testimonies: " + e.getMessage()));
        }
    }

    @GetMapping("/category/{category}")
    @Operation(summary = "Get testimonies by category")
    public ResponseEntity<?> getTestimoniesByCategory(
            @PathVariable Testimony.TestimonyCategory category,
            @PageableDefault(size = 10) Pageable pageable) {
        try {
//            List<TestimonyDTO> testimonies = testimonyService.getTestimoniesByCategory(category);
            Page<TestimonyDTO> testimonies = testimonyService.getTestimoniesByCategory(category, pageable);
            return ResponseEntity.ok(testimonies);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponseDTO.of("Error retrieving testimonies: " + e.getMessage()));
        }
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get testimonies by user")
    public ResponseEntity<?> getTestimoniesByUser(
            @PathVariable Long userId,
            @PageableDefault(size = 10) Pageable pageable) {
        try {
            User user = userService.findUserById(userId);
            Page<TestimonyDTO> testimonies = testimonyService.getTestimoniesByUser(user, pageable);
            return ResponseEntity.ok(testimonies);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(MessageResponseDTO.of(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponseDTO.of("Error retrieving testimonies: " + e.getMessage()));
        }
    }


    @GetMapping("/date-range")
    @Operation(summary = "Get testimonies by date range")
    public ResponseEntity<?> getTestimoniesByDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate,
            @PageableDefault(size = 10) Pageable pageable) {
        try {
            List<TestimonyDTO> testimonies = (List<TestimonyDTO>) testimonyService.getTestimoniesByDateRange(startDate, endDate, pageable);
            return ResponseEntity.ok(testimonies);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponseDTO.of("Error retrieving testimonies: " + e.getMessage()));
        }
    }

    @PostMapping("/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Approve a testimony")
    public ResponseEntity<?> approveTestimony(
            @PathVariable Long id,
            Authentication authentication) {
        try {
            User user = userService.findUserByEmail(authentication.getName());
            TestimonyDTO testimony = testimonyService.approveTestimony(id, user);
            return ResponseEntity.ok(testimony);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(MessageResponseDTO.of(e.getMessage()));
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(MessageResponseDTO.of(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponseDTO.of("Error approving testimony: " + e.getMessage()));
        }
    }

    @PostMapping("/{id}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Reject a testimony")
    public ResponseEntity<?> rejectTestimony(
            @PathVariable Long id,
            @RequestParam String rejectionReason,
            Authentication authentication) {
        try {
            User user = userService.findUserByEmail(authentication.getName());
            TestimonyDTO testimony = testimonyService.rejectTestimony(id, user, rejectionReason);
            return ResponseEntity.ok(testimony);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(MessageResponseDTO.of(e.getMessage()));
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(MessageResponseDTO.of(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponseDTO.of("Error rejecting testimony: " + e.getMessage()));
        }
    }

    @PostMapping("/{id}/like")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Like a testimony")
    public ResponseEntity<?> likeTestimony(
            @PathVariable Long id,
            Authentication authentication) {
        try {
            User user = userService.findUserByEmail(authentication.getName());
            TestimonyDTO testimony = testimonyService.likeTestimony(id, user);
            return ResponseEntity.ok(testimony);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(MessageResponseDTO.of(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponseDTO.of("Error liking testimony: " + e.getMessage()));
        }
    }

    @PostMapping("/{id}/unlike")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Unlike a testimony")
    public ResponseEntity<?> unlikeTestimony(
            @PathVariable Long id,
            Authentication authentication) {
        try {
            User user = userService.findUserByEmail(authentication.getName());
            TestimonyDTO testimony = testimonyService.unlikeTestimony(id, user);
            return ResponseEntity.ok(testimony);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(MessageResponseDTO.of(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponseDTO.of("Error unliking testimony: " + e.getMessage()));
        }
    }

    @GetMapping("/status/{status}/count")
    @Operation(summary = "Count testimonies by status")
    public ResponseEntity<?> countTestimoniesByStatus(
            @PathVariable Testimony.TestimonyStatus status) {
        try {
            long count = testimonyService.countTestimoniesByStatus(status);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponseDTO.of("Error counting testimonies: " + e.getMessage()));
        }
    }

    @GetMapping("/category/{category}/count")
    @Operation(summary = "Count testimonies by category")
    public ResponseEntity<?> countTestimoniesByCategory(
            @PathVariable Testimony.TestimonyCategory category) {
        try {
            long count = testimonyService.countTestimoniesByCategory(category);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponseDTO.of("Error counting testimonies: " + e.getMessage()));
        }
    }

    @GetMapping("/most-liked")
    @Operation(summary = "Get most liked testimonies")
    public ResponseEntity<?> getMostLikedTestimonies(
            @RequestParam(defaultValue = "10") int limit) {
        try {
            List<TestimonyDTO> testimonies = testimonyService.getMostLikedTestimonies(limit);
            return ResponseEntity.ok(testimonies);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponseDTO.of("Error retrieving testimonies: " + e.getMessage()));
        }
    }

    @GetMapping("/most-shared")
    @Operation(summary = "Get most shared testimonies")
    public ResponseEntity<?> getMostSharedTestimonies(
            @RequestParam(defaultValue = "10") int limit) {
        try {
            List<TestimonyDTO> testimonies = testimonyService.getMostSharedTestimonies(limit);
            return ResponseEntity.ok(testimonies);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponseDTO.of("Error retrieving testimonies: " + e.getMessage()));
        }
    }

    @PostMapping("/{id}/share")
    @Operation(summary = "Share a testimony")
    public ResponseEntity<?> shareTestimony(@PathVariable Long id) {
        try {
            TestimonyDTO testimony = testimonyService.shareTestimony(id);
            return ResponseEntity.ok(testimony);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(MessageResponseDTO.of(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponseDTO.of("Error sharing testimony: " + e.getMessage()));
        }
    }
} 