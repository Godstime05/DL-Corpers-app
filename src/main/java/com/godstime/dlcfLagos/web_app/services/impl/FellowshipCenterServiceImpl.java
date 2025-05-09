package com.godstime.dlcfLagos.web_app.services.impl;

import com.godstime.dlcfLagos.web_app.dto.FellowshipCenterDTO;
import com.godstime.dlcfLagos.web_app.exception.ResourceNotFoundException;
import com.godstime.dlcfLagos.web_app.exception.UnauthorizedException;
import com.godstime.dlcfLagos.web_app.models.FellowshipCenter;
import com.godstime.dlcfLagos.web_app.models.User;
import com.godstime.dlcfLagos.web_app.repositories.FellowshipCenterRepository;
import com.godstime.dlcfLagos.web_app.repositories.UserRepository;
import com.godstime.dlcfLagos.web_app.services.FellowshipCenterService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FellowshipCenterServiceImpl implements FellowshipCenterService {
    private final FellowshipCenterRepository fellowshipCenterRepository;
    private final UserRepository userRepository;

    @Override
    public FellowshipCenterDTO createFellowshipCenter(FellowshipCenterDTO centerDTO, Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        FellowshipCenter center = new FellowshipCenter();
        center.setName(centerDTO.getName());
        center.setAddress(centerDTO.getAddress());
        center.setCity(centerDTO.getCity());
        center.setState(centerDTO.getState());
        center.setCountry(centerDTO.getCountry());
        center.setPostalCode(centerDTO.getPostalCode());
        center.setContactPhone(centerDTO.getContactPhone());
        center.setContactEmail(centerDTO.getContactEmail());
        center.setCapacity(centerDTO.getCapacity());
        center.setActive(true);
        center.setCreatedBy(user);

        return FellowshipCenterDTO.fromEntity(fellowshipCenterRepository.save(center));
    }

    @Override
    public FellowshipCenterDTO updateFellowshipCenter(Long id, FellowshipCenterDTO centerDTO, Long userId) {
        FellowshipCenter center = fellowshipCenterRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Fellowship center not found with id: " + id));

        if (!center.getCreatedBy().getId().equals(userId)) {
            throw new UnauthorizedException("You are not authorized to update this fellowship center");
        }

        center.setName(centerDTO.getName());
        center.setAddress(centerDTO.getAddress());
        center.setCity(centerDTO.getCity());
        center.setState(centerDTO.getState());
        center.setCountry(centerDTO.getCountry());
        center.setPostalCode(centerDTO.getPostalCode());
        center.setContactPhone(centerDTO.getContactPhone());
        center.setContactEmail(centerDTO.getContactEmail());
        center.setCapacity(centerDTO.getCapacity());

        return FellowshipCenterDTO.fromEntity(fellowshipCenterRepository.save(center));
    }

    @Override
    public void deleteFellowshipCenter(Long id, Long userId) {
        FellowshipCenter center = fellowshipCenterRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Fellowship center not found with id: " + id));

        if (!center.getCreatedBy().getId().equals(userId)) {
            throw new UnauthorizedException("You are not authorized to delete this fellowship center");
        }

        fellowshipCenterRepository.delete(center);
    }

    @Override
    public FellowshipCenterDTO getFellowshipCenterById(Long id) {
        FellowshipCenter center = fellowshipCenterRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Fellowship center not found with id: " + id));
        return FellowshipCenterDTO.fromEntity(center);
    }

    @Override
    public Page<FellowshipCenterDTO> getAllFellowshipCenters(Pageable pageable) {
        return fellowshipCenterRepository.findAll(pageable).map(FellowshipCenterDTO::fromEntity);
    }

    @Override
    public Page<FellowshipCenterDTO> getActiveFellowshipCenters(Pageable pageable) {
        return fellowshipCenterRepository.findByIsActiveTrue(pageable).map(FellowshipCenterDTO::fromEntity);
    }

    @Override
    public Page<FellowshipCenterDTO> getFellowshipCentersByCity(String city, Pageable pageable) {
        return fellowshipCenterRepository.findByCity(city, pageable).map(FellowshipCenterDTO::fromEntity);
    }

    @Override
    public Page<FellowshipCenterDTO> getFellowshipCentersByState(String state, Pageable pageable) {
        return fellowshipCenterRepository.findByState(state, pageable).map(FellowshipCenterDTO::fromEntity);
    }

    @Override
    public Page<FellowshipCenterDTO> getFellowshipCentersByUser(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        return fellowshipCenterRepository.findByCreatedBy(user, pageable).map(FellowshipCenterDTO::fromEntity);
    }

    @Override
    public long countActiveFellowshipCenters() {
        return fellowshipCenterRepository.countByIsActiveTrue();
    }

    @Override
    public long countFellowshipCentersByCity(String city) {
        return fellowshipCenterRepository.countByCity(city);
    }

    @Override
    public long countFellowshipCentersByState(String state) {
        return fellowshipCenterRepository.countByState(state);
    }

    @Override
    public boolean existsById(Long id) {
        return fellowshipCenterRepository.existsById(id);
    }

    @Override
    public FellowshipCenterDTO toggleFellowshipCenterStatus(Long id, Long userId) {
        FellowshipCenter center = fellowshipCenterRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Fellowship center not found with id: " + id));

        if (!center.getCreatedBy().getId().equals(userId)) {
            throw new UnauthorizedException("You are not authorized to toggle this fellowship center's status");
        }

        center.setActive(!center.isActive());
        return FellowshipCenterDTO.fromEntity(fellowshipCenterRepository.save(center));
    }
} 