package com.godstime.dlcfLagos.web_app.service.impl;

import com.godstime.dlcfLagos.web_app.dto.EvangelismRecordDTO;
import com.godstime.dlcfLagos.web_app.exception.ResourceNotFoundException;
import com.godstime.dlcfLagos.web_app.exception.UnauthorizedException;
import com.godstime.dlcfLagos.web_app.model.EvangelismRecord;
import com.godstime.dlcfLagos.web_app.model.FellowshipCenter;
import com.godstime.dlcfLagos.web_app.model.User;
import com.godstime.dlcfLagos.web_app.repository.EvangelismRecordRepository;
import com.godstime.dlcfLagos.web_app.repository.FellowshipCenterRepository;
import com.godstime.dlcfLagos.web_app.repository.UserRepository;
import com.godstime.dlcfLagos.web_app.service.EvangelismRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;

@Service
@Transactional
@RequiredArgsConstructor
public class EvangelismRecordServiceImpl implements EvangelismRecordService {
    private final EvangelismRecordRepository evangelismRecordRepository;
    private final FellowshipCenterRepository fellowshipCenterRepository;
    private final UserRepository userRepository;

    @Override
    public EvangelismRecordDTO createEvangelismRecord(EvangelismRecordDTO recordDTO, Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        FellowshipCenter center = fellowshipCenterRepository.findById(recordDTO.getFellowshipCenterId())
            .orElseThrow(() -> new ResourceNotFoundException("Fellowship center not found with id: " + recordDTO.getFellowshipCenterId()));

        EvangelismRecord record = new EvangelismRecord();
        record.setLocation(recordDTO.getLocation());
        record.setEvangelismDate(recordDTO.getEvangelismDate());
        record.setNumberOfSoulsWon(recordDTO.getNumberOfSoulsWon());
        record.setNumberOfFollowUps(recordDTO.getNumberOfFollowUps());
        record.setReport(recordDTO.getReport());
        record.setFellowshipCenter(center);
        record.setCreatedBy(user);
        record.setTeamMembers(new HashSet<>());

        return EvangelismRecordDTO.fromEntity(evangelismRecordRepository.save(record));
    }

    @Override
    public EvangelismRecordDTO updateEvangelismRecord(Long id, EvangelismRecordDTO recordDTO, Long userId) {
        EvangelismRecord record = evangelismRecordRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Evangelism record not found with id: " + id));

        if (!record.getCreatedBy().getId().equals(userId)) {
            throw new UnauthorizedException("You are not authorized to update this evangelism record");
        }

        record.setLocation(recordDTO.getLocation());
        record.setEvangelismDate(recordDTO.getEvangelismDate());
        record.setNumberOfSoulsWon(recordDTO.getNumberOfSoulsWon());
        record.setNumberOfFollowUps(recordDTO.getNumberOfFollowUps());
        record.setReport(recordDTO.getReport());

        return EvangelismRecordDTO.fromEntity(evangelismRecordRepository.save(record));
    }

    @Override
    public void deleteEvangelismRecord(Long id, Long userId) {
        EvangelismRecord record = evangelismRecordRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Evangelism record not found with id: " + id));

        if (!record.getCreatedBy().getId().equals(userId)) {
            throw new UnauthorizedException("You are not authorized to delete this evangelism record");
        }

        evangelismRecordRepository.delete(record);
    }

    @Override
    public EvangelismRecordDTO getEvangelismRecordById(Long id) {
        EvangelismRecord record = evangelismRecordRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Evangelism record not found with id: " + id));
        return EvangelismRecordDTO.fromEntity(record);
    }

    @Override
    public Page<EvangelismRecordDTO> getAllEvangelismRecords(Pageable pageable) {
        return evangelismRecordRepository.findAll(pageable).map(EvangelismRecordDTO::fromEntity);
    }

    @Override
    public Page<EvangelismRecordDTO> getEvangelismRecordsByFellowshipCenter(Long centerId, Pageable pageable) {
        FellowshipCenter center = fellowshipCenterRepository.findById(centerId)
            .orElseThrow(() -> new ResourceNotFoundException("Fellowship center not found with id: " + centerId));
        return evangelismRecordRepository.findByFellowshipCenter(center, pageable).map(EvangelismRecordDTO::fromEntity);
    }

    @Override
    public Page<EvangelismRecordDTO> getEvangelismRecordsByUser(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        return evangelismRecordRepository.findByCreatedBy(user, pageable).map(EvangelismRecordDTO::fromEntity);
    }

    @Override
    public Page<EvangelismRecordDTO> getEvangelismRecordsByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return evangelismRecordRepository.findByEvangelismDateBetween(startDate, endDate, pageable)
            .map(EvangelismRecordDTO::fromEntity);
    }

    @Override
    public Page<EvangelismRecordDTO> getEvangelismRecordsByLocation(String location, Pageable pageable) {
        return evangelismRecordRepository.findByLocationContainingIgnoreCase(location, pageable)
            .map(EvangelismRecordDTO::fromEntity);
    }

    @Override
    public long countEvangelismRecordsByFellowshipCenter(Long centerId) {
        FellowshipCenter center = fellowshipCenterRepository.findById(centerId)
            .orElseThrow(() -> new ResourceNotFoundException("Fellowship center not found with id: " + centerId));
        return evangelismRecordRepository.countByFellowshipCenter(center);
    }

    @Override
    public long countEvangelismRecordsByUser(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        return evangelismRecordRepository.countByCreatedBy(user);
    }

    @Override
    public long countEvangelismRecordsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return evangelismRecordRepository.countByEvangelismDateBetween(startDate, endDate);
    }

    @Override
    public boolean existsById(Long id) {
        return evangelismRecordRepository.existsById(id);
    }

    @Override
    public EvangelismRecordDTO addTeamMember(Long recordId, Long userId, Long currentUserId) {
        EvangelismRecord record = evangelismRecordRepository.findById(recordId)
            .orElseThrow(() -> new ResourceNotFoundException("Evangelism record not found with id: " + recordId));

        if (!record.getCreatedBy().getId().equals(currentUserId)) {
            throw new UnauthorizedException("You are not authorized to modify this evangelism record's team");
        }

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        record.getTeamMembers().add(user);
        return EvangelismRecordDTO.fromEntity(evangelismRecordRepository.save(record));
    }

    @Override
    public EvangelismRecordDTO removeTeamMember(Long recordId, Long userId, Long currentUserId) {
        EvangelismRecord record = evangelismRecordRepository.findById(recordId)
            .orElseThrow(() -> new ResourceNotFoundException("Evangelism record not found with id: " + recordId));

        if (!record.getCreatedBy().getId().equals(currentUserId)) {
            throw new UnauthorizedException("You are not authorized to modify this evangelism record's team");
        }

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        record.getTeamMembers().remove(user);
        return EvangelismRecordDTO.fromEntity(evangelismRecordRepository.save(record));
    }
} 