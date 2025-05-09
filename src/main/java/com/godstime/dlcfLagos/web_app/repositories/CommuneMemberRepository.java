package com.godstime.dlcfLagos.web_app.repositories;

import com.godstime.dlcfLagos.web_app.models.CommuneMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommuneMemberRepository extends JpaRepository<CommuneMember, Long> {
    List<CommuneMember> findByCommuneId(Long communeId);
    List<CommuneMember> findByCommuneIdAndNameContainingIgnoreCase(Long communeId, String name);
    boolean existsByIdAndCommuneId(Long memberId, Long communeId);
} 