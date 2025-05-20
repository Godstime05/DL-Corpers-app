package com.godstime.dlcfLagos.web_app.dto;

import com.godstime.dlcfLagos.web_app.models.FellowshipCenter;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FellowshipCenterDTO {
    private Long id;

    @NotBlank(message = "Center name is required")
    private String name;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "Country is required")
    private String country;

    private String postalCode;
    private String contactPhone;
    private String contactEmail;
    private Integer capacity;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long createdById;
    private String createdByFullName;

    public static FellowshipCenterDTO fromEntity(FellowshipCenter center) {
        FellowshipCenterDTO dto = new FellowshipCenterDTO();
        dto.setId(center.getId());
        dto.setName(center.getName());
        dto.setAddress(center.getAddress());
        dto.setCity(center.getCity());
        dto.setState(center.getState());
        dto.setCountry(center.getCountry());
        dto.setPostalCode(center.getPostalCode());
        dto.setContactPhone(center.getContactPhone());
        dto.setContactEmail(center.getContactEmail());
        dto.setCapacity(center.getCapacity());
        dto.setActive(center.isActive());
        dto.setCreatedAt(center.getCreatedAt());
        dto.setUpdatedAt(center.getUpdatedAt());
        
        if (center.getCreatedBy() != null) {
            dto.setCreatedById(center.getCreatedBy().getId());
            dto.setCreatedByFullName(center.getCreatedBy().getUsername());
        }
        
        return dto;
    }
} 