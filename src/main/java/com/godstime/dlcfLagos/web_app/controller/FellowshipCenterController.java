package com.godstime.dlcfLagos.web_app.controller;

import com.godstime.dlcfLagos.web_app.dto.FellowshipCenterDTO;
import com.godstime.dlcfLagos.web_app.dto.MessageResponseDTO;
import com.godstime.dlcfLagos.web_app.exception.ResourceNotFoundException;
import com.godstime.dlcfLagos.web_app.exception.UnauthorizedException;
import com.godstime.dlcfLagos.web_app.service.FellowshipCenterService;
import com.godstime.dlcfLagos.web_app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
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

@RestController
@RequestMapping("/api/fellowship-centers")
@Tag(name = "Fellowship Center Controller", description = "APIs for managing fellowship centers")
public class FellowshipCenterController {
    private final FellowshipCenterService fellowshipCenterService;
    private final UserService userService;

    public FellowshipCenterController(FellowshipCenterService fellowshipCenterService, UserService userService) {
        this.fellowshipCenterService = fellowshipCenterService;
        this.userService = userService;
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Create a new fellowship center")
    public ResponseEntity<?> createFellowshipCenter(
            @Valid @RequestBody FellowshipCenterDTO centerDTO,
            Authentication authentication) {
        try {
            Long userId = userService.findUserByEmail(authentication.getName()).getId();
            FellowshipCenterDTO createdCenter = fellowshipCenterService.createFellowshipCenter(centerDTO, userId);
            return ResponseEntity.ok(createdCenter);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponseDTO.of("Error creating fellowship center: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Update an existing fellowship center")
    public ResponseEntity<?> updateFellowshipCenter(
            @PathVariable Long id,
            @Valid @RequestBody FellowshipCenterDTO centerDTO,
            Authentication authentication) {
        try {
            Long userId = userService.findUserByEmail(authentication.getName()).getId();
            FellowshipCenterDTO updatedCenter = fellowshipCenterService.updateFellowshipCenter(id, centerDTO, userId);
            return ResponseEntity.ok(updatedCenter);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(MessageResponseDTO.of(e.getMessage()));
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(MessageResponseDTO.of(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponseDTO.of("Error updating fellowship center: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Delete a fellowship center")
    public ResponseEntity<?> deleteFellowshipCenter(
            @PathVariable Long id,
            Authentication authentication) {
        try {
            Long userId = userService.findUserByEmail(authentication.getName()).getId();
            fellowshipCenterService.deleteFellowshipCenter(id, userId);
            return ResponseEntity.ok(MessageResponseDTO.of("Fellowship center deleted successfully"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(MessageResponseDTO.of(e.getMessage()));
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(MessageResponseDTO.of(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponseDTO.of("Error deleting fellowship center: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a fellowship center by ID")
    public ResponseEntity<?> getFellowshipCenterById(@PathVariable Long id) {
        try {
            FellowshipCenterDTO center = fellowshipCenterService.getFellowshipCenterById(id);
            return ResponseEntity.ok(center);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(MessageResponseDTO.of(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponseDTO.of("Error retrieving fellowship center: " + e.getMessage()));
        }
    }

    @GetMapping
    @Operation(summary = "Get all fellowship centers")
    public ResponseEntity<?> getAllFellowshipCenters(
            @PageableDefault(size = 10) Pageable pageable) {
        try {
            Page<FellowshipCenterDTO> centers = fellowshipCenterService.getAllFellowshipCenters(pageable);
            return ResponseEntity.ok(centers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponseDTO.of("Error retrieving fellowship centers: " + e.getMessage()));
        }
    }

    @GetMapping("/active")
    @Operation(summary = "Get all active fellowship centers")
    public ResponseEntity<?> getActiveFellowshipCenters(
            @PageableDefault(size = 10) Pageable pageable) {
        try {
            Page<FellowshipCenterDTO> centers = fellowshipCenterService.getActiveFellowshipCenters(pageable);
            return ResponseEntity.ok(centers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponseDTO.of("Error retrieving active fellowship centers: " + e.getMessage()));
        }
    }

    @GetMapping("/city/{city}")
    @Operation(summary = "Get fellowship centers by city")
    public ResponseEntity<?> getFellowshipCentersByCity(
            @PathVariable String city,
            @PageableDefault(size = 10) Pageable pageable) {
        try {
            Page<FellowshipCenterDTO> centers = fellowshipCenterService.getFellowshipCentersByCity(city, pageable);
            return ResponseEntity.ok(centers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponseDTO.of("Error retrieving fellowship centers: " + e.getMessage()));
        }
    }

    @GetMapping("/state/{state}")
    @Operation(summary = "Get fellowship centers by state")
    public ResponseEntity<?> getFellowshipCentersByState(
            @PathVariable String state,
            @PageableDefault(size = 10) Pageable pageable) {
        try {
            Page<FellowshipCenterDTO> centers = fellowshipCenterService.getFellowshipCentersByState(state, pageable);
            return ResponseEntity.ok(centers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponseDTO.of("Error retrieving fellowship centers: " + e.getMessage()));
        }
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get fellowship centers by user")
    public ResponseEntity<?> getFellowshipCentersByUser(
            @PathVariable Long userId,
            @PageableDefault(size = 10) Pageable pageable) {
        try {
            Page<FellowshipCenterDTO> centers = fellowshipCenterService.getFellowshipCentersByUser(userId, pageable);
            return ResponseEntity.ok(centers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponseDTO.of("Error retrieving fellowship centers: " + e.getMessage()));
        }
    }

    @GetMapping("/count/active")
    @Operation(summary = "Count active fellowship centers")
    public ResponseEntity<?> countActiveFellowshipCenters() {
        try {
            long count = fellowshipCenterService.countActiveFellowshipCenters();
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponseDTO.of("Error counting fellowship centers: " + e.getMessage()));
        }
    }

    @GetMapping("/count/city/{city}")
    @Operation(summary = "Count fellowship centers by city")
    public ResponseEntity<?> countFellowshipCentersByCity(@PathVariable String city) {
        try {
            long count = fellowshipCenterService.countFellowshipCentersByCity(city);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponseDTO.of("Error counting fellowship centers: " + e.getMessage()));
        }
    }

    @GetMapping("/count/state/{state}")
    @Operation(summary = "Count fellowship centers by state")
    public ResponseEntity<?> countFellowshipCentersByState(@PathVariable String state) {
        try {
            long count = fellowshipCenterService.countFellowshipCentersByState(state);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponseDTO.of("Error counting fellowship centers: " + e.getMessage()));
        }
    }

    @PostMapping("/{id}/toggle-status")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Toggle fellowship center status")
    public ResponseEntity<?> toggleFellowshipCenterStatus(
            @PathVariable Long id,
            Authentication authentication) {
        try {
            Long userId = userService.findUserByEmail(authentication.getName()).getId();
            FellowshipCenterDTO center = fellowshipCenterService.toggleFellowshipCenterStatus(id, userId);
            return ResponseEntity.ok(center);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(MessageResponseDTO.of(e.getMessage()));
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(MessageResponseDTO.of(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponseDTO.of("Error toggling fellowship center status: " + e.getMessage()));
        }
    }
} 