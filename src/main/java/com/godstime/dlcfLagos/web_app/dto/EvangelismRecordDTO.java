package com.godstime.dlcfLagos.web_app.dto;

import com.godstime.dlcfLagos.web_app.model.EvangelismRecord;
import com.godstime.dlcfLagos.web_app.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvangelismRecordDTO {
    private Long id;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Date is required")
    private LocalDateTime evangelismDate;

    private Integer numberOfSoulsWon;
    private Integer numberOfFollowUps;
    private String report;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long fellowshipCenterId;
    private String fellowshipCenterName;
    private Long createdById;
    private String createdByFullName;
    private Set<Long> teamMemberIds;
    private Set<String> teamMemberNames;

    public static EvangelismRecordDTO fromEntity(EvangelismRecord record) {
        EvangelismRecordDTO dto = new EvangelismRecordDTO();
        dto.setId(record.getId());
        dto.setLocation(record.getLocation());
        dto.setEvangelismDate(record.getEvangelismDate());
        dto.setNumberOfSoulsWon(record.getNumberOfSoulsWon());
        dto.setNumberOfFollowUps(record.getNumberOfFollowUps());
        dto.setReport(record.getReport());
        dto.setCreatedAt(record.getCreatedAt());
        dto.setUpdatedAt(record.getUpdatedAt());
        
        if (record.getFellowshipCenter() != null) {
            dto.setFellowshipCenterId(record.getFellowshipCenter().getId());
            dto.setFellowshipCenterName(record.getFellowshipCenter().getName());
        }
        
        if (record.getCreatedBy() != null) {
            dto.setCreatedById(record.getCreatedBy().getId());
            dto.setCreatedByFullName(record.getCreatedBy().getFullName());
        }
        
        if (record.getTeamMembers() != null) {
            dto.setTeamMemberIds(record.getTeamMembers().stream()
                .map(User::getId)
                .collect(Collectors.toSet()));
            dto.setTeamMemberNames(record.getTeamMembers().stream()
                .map(User::getFullName)
                .collect(Collectors.toSet()));
        }
        
        return dto;
    }
} 