package com.godstime.dlcfLagos.web_app.repositories;

import com.godstime.dlcfLagos.web_app.models.CorpersCommune;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommuneRepository extends JpaRepository<CorpersCommune, Long> {
    List<CorpersCommune> findByNameContainingIgnoreCaseOrLocationContainingIgnoreCase(String name, String location);
} 