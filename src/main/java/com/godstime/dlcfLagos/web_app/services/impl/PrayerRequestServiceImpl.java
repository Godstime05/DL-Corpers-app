package com.godstime.dlcfLagos.web_app.services.impl;

import com.godstime.dlcfLagos.web_app.dto.PrayerRequestDTO;
import com.godstime.dlcfLagos.web_app.models.PrayerRequest;
import com.godstime.dlcfLagos.web_app.models.User;
import com.godstime.dlcfLagos.web_app.repositories.PrayerRequestRepository;
import com.godstime.dlcfLagos.web_app.services.PrayerRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PrayerRequestServiceImpl implements PrayerRequestService {

    @Autowired
    private PrayerRequestRepository prayerRequestRepository;

    @Override
    public PrayerRequestDTO createPrayerRequest(PrayerRequestDTO prayerRequestDTO, User submittedBy) {
        PrayerRequest prayerRequest = new PrayerRequest();
        mapDTOToEntity(prayerRequestDTO, prayerRequest);
        prayerRequest.setSubmittedBy(submittedBy);
        prayerRequest.setSubmittedAt(LocalDateTime.now());
        prayerRequest.setStatus(PrayerRequest.PrayerStatus.PENDING);
        prayerRequest.setPrayerCount(0);
        return mapEntityToDTO(prayerRequestRepository.save(prayerRequest));
    }

    @Override
    public PrayerRequestDTO updatePrayerRequest(Long id, PrayerRequestDTO prayerRequestDTO, User updatedBy) {
        PrayerRequest prayerRequest = prayerRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prayer request not found"));
        
        if (!prayerRequest.getSubmittedBy().equals(updatedBy)) {
            throw new RuntimeException("Unauthorized to update this prayer request");
        }
        
        mapDTOToEntity(prayerRequestDTO, prayerRequest);
        prayerRequest.setUpdatedAt(LocalDateTime.now());
        return mapEntityToDTO(prayerRequestRepository.save(prayerRequest));
    }

    @Override
    public PrayerRequestDTO respondToPrayerRequest(Long id, String response, User respondedBy) {
        PrayerRequest prayerRequest = prayerRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prayer request not found"));
        
        prayerRequest.setResponse(response);
        prayerRequest.setRespondedBy(respondedBy);
        prayerRequest.setRespondedAt(LocalDateTime.now());
        prayerRequest.setStatus(PrayerRequest.PrayerStatus.ANSWERED);
        
        return mapEntityToDTO(prayerRequestRepository.save(prayerRequest));
    }

    @Override
    public PrayerRequestDTO updatePrayerRequestStatus(Long id, PrayerRequest.PrayerStatus status, User updatedBy) {
        PrayerRequest prayerRequest = prayerRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prayer request not found"));
        
        prayerRequest.setStatus(status);
        prayerRequest.setUpdatedAt(LocalDateTime.now());
        return mapEntityToDTO(prayerRequestRepository.save(prayerRequest));
    }

    @Override
    public void deletePrayerRequest(Long id, User deletedBy) {
        PrayerRequest prayerRequest = prayerRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prayer request not found"));
        
        if (!prayerRequest.getSubmittedBy().equals(deletedBy)) {
            throw new RuntimeException("Unauthorized to delete this prayer request");
        }
        
        prayerRequestRepository.delete(prayerRequest);
    }

    @Override
    public PrayerRequestDTO getPrayerRequestById(Long id) {
        return prayerRequestRepository.findById(id)
                .map(this::mapEntityToDTO)
                .orElseThrow(() -> new RuntimeException("Prayer request not found"));
    }

    @Override
    public List<PrayerRequestDTO> getAllPrayerRequests() {
        return prayerRequestRepository.findAll().stream()
                .map(this::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PrayerRequestDTO> getPrayerRequestsByUser(User user) {
        return prayerRequestRepository.findBySubmittedBy(user).stream()
                .map(this::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PrayerRequestDTO> getPrayerRequestsByCategory(PrayerRequest.PrayerCategory category) {
        return prayerRequestRepository.findByCategory(category).stream()
                .map(this::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PrayerRequestDTO> getPrayerRequestsByStatus(PrayerRequest.PrayerStatus status) {
        return prayerRequestRepository.findByStatus(status).stream()
                .map(this::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PrayerRequestDTO> getAnonymousPrayerRequests() {
        return prayerRequestRepository.findByIsAnonymous(true).stream()
                .map(this::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PrayerRequestDTO> getAnsweredPrayerRequests() {
        return prayerRequestRepository.findByStatus(PrayerRequest.PrayerStatus.ANSWERED).stream()
                .map(this::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PrayerRequestDTO> getUnansweredPrayerRequests() {
        return prayerRequestRepository.findByStatusNot(PrayerRequest.PrayerStatus.ANSWERED).stream()
                .map(this::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PrayerRequestDTO> getMostPrayedForRequests(int limit) {
        return prayerRequestRepository.findAll(PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "prayerCount")))
                .stream()
                .map(this::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PrayerRequestDTO incrementPrayerCount(Long id) {
        PrayerRequest prayerRequest = prayerRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prayer request not found"));
        
        prayerRequest.setPrayerCount(prayerRequest.getPrayerCount() + 1);
        return mapEntityToDTO(prayerRequestRepository.save(prayerRequest));
    }

    @Override
    public long countPrayerRequestsByStatus(PrayerRequest.PrayerStatus status) {
        return prayerRequestRepository.countByStatus(status);
    }

    @Override
    public long countPrayerRequestsByCategory(PrayerRequest.PrayerCategory category) {
        return prayerRequestRepository.countByCategory(category);
    }

    @Override
    public boolean existsById(Long id) {
        return prayerRequestRepository.existsById(id);
    }

    private PrayerRequestDTO mapEntityToDTO(PrayerRequest prayerRequest) {
        PrayerRequestDTO dto = new PrayerRequestDTO();
        dto.setId(prayerRequest.getId());
        dto.setTitle(prayerRequest.getTitle());
        dto.setContent(prayerRequest.getContent());
        dto.setSubmittedById(prayerRequest.getSubmittedBy().getId());
        dto.setSubmittedAt(prayerRequest.getSubmittedAt());
        dto.setUpdatedAt(prayerRequest.getUpdatedAt());
        dto.setAnonymous(prayerRequest.isAnonymous());
        dto.setCategory(prayerRequest.getCategory());
        dto.setStatus(prayerRequest.getStatus());
        dto.setPrayerCount(prayerRequest.getPrayerCount());
        dto.setResponse(prayerRequest.getResponse());
        if (prayerRequest.getRespondedBy() != null) {
            dto.setRespondedById(prayerRequest.getRespondedBy().getId());
        }
        dto.setRespondedAt(prayerRequest.getRespondedAt());
        return dto;
    }

    private void mapDTOToEntity(PrayerRequestDTO dto, PrayerRequest prayerRequest) {
        prayerRequest.setTitle(dto.getTitle());
        prayerRequest.setContent(dto.getContent());
        prayerRequest.setCategory(dto.getCategory());
        prayerRequest.setAnonymous(dto.isAnonymous());
    }
} 