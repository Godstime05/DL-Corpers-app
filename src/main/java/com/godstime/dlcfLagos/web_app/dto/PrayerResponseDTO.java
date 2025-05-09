package com.godstime.dlcfLagos.web_app.dto;

import com.godstime.dlcfLagos.web_app.models.PrayerResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PrayerResponseDTO {
    
    private Long id;
    
    @NotNull(message = "Prayer request ID is required")
    private Long prayerRequestId;
    
    private Long respondedById;
    
    @NotBlank(message = "Response content is required")
    private String response;
    
    private LocalDateTime respondedAt;
    
    private boolean isAnonymous;
    
    @NotNull(message = "Response type is required")
    private PrayerResponse.ResponseType type;
    
    public static PrayerResponseDTO fromEntity(PrayerResponse entity) {
        PrayerResponseDTO dto = new PrayerResponseDTO();
        dto.setId(entity.getId());
        dto.setPrayerRequestId(entity.getPrayerRequest().getId());
        dto.setRespondedById(entity.getRespondedBy().getId());
        dto.setResponse(entity.getResponse());
        dto.setRespondedAt(entity.getRespondedAt());
        dto.setAnonymous(entity.isAnonymous());
        dto.setType(entity.getType());
        return dto;
    }
} 