package com.godstime.dlcfLagos.web_app.repositories;

import com.godstime.dlcfLagos.web_app.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    
    Optional<Tag> findByName(String name);
    
    List<Tag> findByNameContainingIgnoreCase(String name);
    
    List<Tag> findByDescriptionContainingIgnoreCase(String description);
    
    List<Tag> findByDiscussionsIsEmpty();
    
    long countByName(String name);
} 