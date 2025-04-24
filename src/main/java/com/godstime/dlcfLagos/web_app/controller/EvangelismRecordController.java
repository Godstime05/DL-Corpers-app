package com.godstime.dlcfLagos.web_app.controller;

import com.godstime.dlcfLagos.web_app.dto.EvangelismRecordDTO;
import com.godstime.dlcfLagos.web_app.dto.MessageResponseDTO;
import com.godstime.dlcfLagos.web_app.exception.ResourceNotFoundException;
import com.godstime.dlcfLagos.web_app.exception.UnauthorizedException;
import com.godstime.dlcfLagos.web_app.service.EvangelismRecordService;
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

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/evangelism-records")
@Tag(name = "Evangelism Record Controller", description = "APIs for managing evangelism records")
public class EvangelismRecordController {
    private final EvangelismRecordService evangelismRecordService;
    private final UserService userService;

    public EvangelismRecordController(EvangelismRecordService evangelismRecordService, UserService userService) {
        this.evangelismRecordService = evangelismRecordService;
        this.userService = userService;
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Create a new evangelism record")
    public ResponseEntity<?> createEvangelismRecord(
            @Valid @RequestBody EvangelismRecordDTO recordDTO,
            Authentication authentication) {
        try {
            Long userId = userService.findUserByEmail(authentication.getName()).getId();
            EvangelismRecordDTO createdRecord = evangelismRecordService.createEvangelismRecord(recordDTO, userId);
            return ResponseEntity.ok(createdRecord);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponseDTO.of("Error creating evangelism record: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Update an existing evangelism record")
    public ResponseEntity<?> updateEvangelismRecord(
            @PathVariable Long id,
            @Valid @RequestBody EvangelismRecordDTO recordDTO,
            Authentication authentication) {
        try {
            Long userId = userService.findUserByEmail(authentication.getName()).getId();
            EvangelismRecordDTO updatedRecord = evangelismRecordService.updateEvangelismRecord(id, recordDTO, userId);
            return ResponseEntity.ok(updatedRecord);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(MessageResponseDTO.of(e.getMessage()));
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(MessageResponseDTO.of(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponseDTO.of("Error updating evangelism record: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Delete an evangelism record")
    public ResponseEntity<?> deleteEvangelismRecord(
            @PathVariable Long id,
            Authentication authentication) {
        try {
            Long userId = userService.findUserByEmail(authentication.getName()).getId();
            evangelismRecordService.deleteEvangelismRecord(id, userId);
            return ResponseEntity.ok(MessageResponseDTO.of("Evangelism record deleted successfully"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(MessageResponseDTO.of(e.getMessage()));
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(MessageResponseDTO.of(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponseDTO.of("Error deleting evangelism record: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an evangelism record by ID")
    public ResponseEntity<?> getEvangelismRecordById(@PathVariable Long id) {
        try {
            EvangelismRecordDTO record = evangelismRecordService.getEvangelismRecordById(id);
            return ResponseEntity.ok(record);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(MessageResponseDTO.of(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponseDTO.of("Error retrieving evangelism record: " + e.getMessage()));
        }
    }

    @GetMapping
    @Operation(summary = "Get all evangelism records")
    public ResponseEntity<?> getAllEvangelismRecords(
            @PageableDefault(size = 10) Pageable pageable) {
        try {
            Page<EvangelismRecordDTO> records = evangelismRecordService.getAllEvangelismRecords(pageable);
            return ResponseEntity.ok(records);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponseDTO.of("Error retrieving evangelism records: " + e.getMessage()));
        }
    }

    @GetMapping("/fellowship-center/{centerId}")
    @Operation(summary = "Get evangelism records by fellowship center")
    public ResponseEntity<?> getEvangelismRecordsByFellowshipCenter(
            @PathVariable Long centerId,
            @PageableDefault(size = 10) Pageable pageable) {
        try {
            Page<EvangelismRecordDTO> records = evangelismRecordService.getEvangelismRecordsByFellowshipCenter(centerId, pageable);
            return ResponseEntity.ok(records);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponseDTO.of("Error retrieving evangelism records: " + e.getMessage()));
        }
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get evangelism records by user")
    public ResponseEntity<?> getEvangelismRecordsByUser(
            @PathVariable Long userId,
            @PageableDefault(size = 10) Pageable pageable) {
        try {
            Page<EvangelismRecordDTO> records = evangelismRecordService.getEvangelismRecordsByUser(userId, pageable);
            return ResponseEntity.ok(records);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponseDTO.of("Error retrieving evangelism records: " + e.getMessage()));
        }
    }

    @GetMapping("/date-range")
    @Operation(summary = "Get evangelism records by date range")
    public ResponseEntity<?> getEvangelismRecordsByDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate,
            @PageableDefault(size = 10) Pageable pageable) {
        try {
            Page<EvangelismRecordDTO> records = evangelismRecordService.getEvangelismRecordsByDateRange(startDate, endDate, pageable);
            return ResponseEntity.ok(records);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponseDTO.of("Error retrieving evangelism records: " + e.getMessage()));
        }
    }

    @GetMapping("/location")
    @Operation(summary = "Get evangelism records by location")
    public ResponseEntity<?> getEvangelismRecordsByLocation(
            @RequestParam String location,
            @PageableDefault(size = 10) Pageable pageable) {
        try {
            Page<EvangelismRecordDTO> records = evangelismRecordService.getEvangelismRecordsByLocation(location, pageable);
            return ResponseEntity.ok(records);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponseDTO.of("Error retrieving evangelism records: " + e.getMessage()));
        }
    }

    @PostMapping("/{id}/add-team-member/{memberId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Add a team member to an evangelism record")
    public ResponseEntity<?> addTeamMember(
            @PathVariable Long id,
            @PathVariable Long memberId,
            Authentication authentication) {
        try {
            Long userId = userService.findUserByEmail(authentication.getName()).getId();
            EvangelismRecordDTO record = evangelismRecordService.addTeamMember(id, memberId, userId);
            return ResponseEntity.ok(record);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(MessageResponseDTO.of(e.getMessage()));
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(MessageResponseDTO.of(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponseDTO.of("Error adding team member: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}/remove-team-member/{memberId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Remove a team member from an evangelism record")
    public ResponseEntity<?> removeTeamMember(
            @PathVariable Long id,
            @PathVariable Long memberId,
            Authentication authentication) {
        try {
            Long userId = userService.findUserByEmail(authentication.getName()).getId();
            EvangelismRecordDTO record = evangelismRecordService.removeTeamMember(id, memberId, userId);
            return ResponseEntity.ok(record);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(MessageResponseDTO.of(e.getMessage()));
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(MessageResponseDTO.of(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponseDTO.of("Error removing team member: " + e.getMessage()));
        }
    }

    @GetMapping("/count/fellowship-center/{centerId}")
    @Operation(summary = "Count evangelism records by fellowship center")
    public ResponseEntity<?> countEvangelismRecordsByFellowshipCenter(@PathVariable Long centerId) {
        try {
            long count = evangelismRecordService.countEvangelismRecordsByFellowshipCenter(centerId);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponseDTO.of("Error counting evangelism records: " + e.getMessage()));
        }
    }

    @GetMapping("/count/user/{userId}")
    @Operation(summary = "Count evangelism records by user")
    public ResponseEntity<?> countEvangelismRecordsByUser(@PathVariable Long userId) {
        try {
            long count = evangelismRecordService.countEvangelismRecordsByUser(userId);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponseDTO.of("Error counting evangelism records: " + e.getMessage()));
        }
    }

    @GetMapping("/count/date-range")
    @Operation(summary = "Count evangelism records by date range")
    public ResponseEntity<?> countEvangelismRecordsByDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        try {
            long count = evangelismRecordService.countEvangelismRecordsByDateRange(startDate, endDate);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponseDTO.of("Error counting evangelism records: " + e.getMessage()));
        }
    }
} 