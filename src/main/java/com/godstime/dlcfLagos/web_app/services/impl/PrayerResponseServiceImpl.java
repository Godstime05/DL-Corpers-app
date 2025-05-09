package com.godstime.dlcfLagos.web_app.services.impl;

import com.godstime.dlcfLagos.web_app.dto.PrayerResponseDTO;
import com.godstime.dlcfLagos.web_app.models.PrayerRequest;
import com.godstime.dlcfLagos.web_app.models.PrayerResponse;
import com.godstime.dlcfLagos.web_app.models.User;
import com.godstime.dlcfLagos.web_app.repositories.PrayerRequestRepository;
import com.godstime.dlcfLagos.web_app.repositories.PrayerResponseRepository;
import com.godstime.dlcfLagos.web_app.repositories.UserRepository;
import com.godstime.dlcfLagos.web_app.services.PrayerResponseService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PrayerResponseServiceImpl implements PrayerResponseService {

    private final PrayerResponseRepository prayerResponseRepository;
    private final PrayerRequestRepository prayerRequestRepository;
    private final UserRepository userRepository;

    public PrayerResponseServiceImpl(
            PrayerResponseRepository prayerResponseRepository,
            PrayerRequestRepository prayerRequestRepository,
            UserRepository userRepository) {
        this.prayerResponseRepository = prayerResponseRepository;
        this.prayerRequestRepository = prayerRequestRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PrayerResponseDTO createResponse(PrayerResponseDTO responseDTO) {
        PrayerRequest prayerRequest = prayerRequestRepository.findById(responseDTO.getPrayerRequestId())
                .orElseThrow(() -> new EntityNotFoundException("Prayer request not found"));
        
        User respondedBy = userRepository.findById(responseDTO.getRespondedById())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        PrayerResponse response = new PrayerResponse();
        response.setPrayerRequest(prayerRequest);
        response.setRespondedBy(respondedBy);
        response.setResponse(responseDTO.getResponse());
        response.setAnonymous(responseDTO.isAnonymous());
        response.setType(responseDTO.getType());

        PrayerResponse savedResponse = prayerResponseRepository.save(response);
        return PrayerResponseDTO.fromEntity(savedResponse);
    }

    @Override
    public PrayerResponseDTO updateResponse(Long id, PrayerResponseDTO responseDTO) {
        PrayerResponse response = prayerResponseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Prayer response not found"));

        response.setResponse(responseDTO.getResponse());
        response.setAnonymous(responseDTO.isAnonymous());
        response.setType(responseDTO.getType());

        PrayerResponse updatedResponse = prayerResponseRepository.save(response);
        return PrayerResponseDTO.fromEntity(updatedResponse);
    }

    @Override
    public void deleteResponse(Long id) {
        if (!prayerResponseRepository.existsById(id)) {
            throw new EntityNotFoundException("Prayer response not found");
        }
        prayerResponseRepository.deleteById(id);
    }

    @Override
    public PrayerResponseDTO getResponseById(Long id) {
        return prayerResponseRepository.findById(id)
                .map(PrayerResponseDTO::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Prayer response not found"));
    }

    @Override
    public Page<PrayerResponseDTO> getResponsesByPrayerRequest(
            Long prayerRequestId,
            Pageable pageable,
            PrayerResponse.ResponseType type,
            Boolean isAnonymous,
            LocalDateTime startDate,
            LocalDateTime endDate) {
        
        Specification<PrayerResponse> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("prayerRequest").get("id"), prayerRequestId));
            
            if (type != null) {
                predicates.add(cb.equal(root.get("type"), type));
            }
            
            if (isAnonymous != null) {
                predicates.add(cb.equal(root.get("isAnonymous"), isAnonymous));
            }
            
            if (startDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("respondedAt"), startDate));
            }
            
            if (endDate != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("respondedAt"), endDate));
            }
            
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        
        return prayerResponseRepository.findAll(spec, pageable)
                .map(PrayerResponseDTO::fromEntity);
    }

    @Override
    public Page<PrayerResponseDTO> getResponsesByUser(Long userId, Pageable pageable) {
        return prayerResponseRepository.findByRespondedById(userId, pageable)
                .map(PrayerResponseDTO::fromEntity);
    }

    @Override
    public Page<PrayerResponseDTO> getNonAnonymousResponses(Long prayerRequestId, Pageable pageable) {
        return prayerResponseRepository.findByPrayerRequestIdAndIsAnonymousFalse(prayerRequestId, pageable)
                .map(PrayerResponseDTO::fromEntity);
    }

    @Override
    public long countResponses(Long prayerRequestId) {
        return prayerResponseRepository.countByPrayerRequestId(prayerRequestId);
    }

    @Override
    public Page<PrayerResponseDTO> getRecentResponses(Pageable pageable, PrayerResponse.ResponseType type) {
        Specification<PrayerResponse> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            if (type != null) {
                predicates.add(cb.equal(root.get("type"), type));
            }
            
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        
        return prayerResponseRepository.findAll(spec, pageable)
                .map(PrayerResponseDTO::fromEntity);
    }
} 