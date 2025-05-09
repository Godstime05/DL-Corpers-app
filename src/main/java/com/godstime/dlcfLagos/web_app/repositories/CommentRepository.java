package com.godstime.dlcfLagos.web_app.repositories;

import com.godstime.dlcfLagos.web_app.models.Comment;
import com.godstime.dlcfLagos.web_app.models.Discussion;
import com.godstime.dlcfLagos.web_app.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    
    List<Comment> findByDiscussion(Discussion discussion);
    
    List<Comment> findByUser(User user);
    
    List<Comment> findByDiscussionAndUser(Discussion discussion, User user);
    
    List<Comment> findByDiscussionOrderByCreatedAtDesc(Discussion discussion);
    
    List<Comment> findByDiscussionOrderByLikesCountDesc(Discussion discussion);
    
    List<Comment> findByIsEdited(boolean isEdited);
    
    Page<Comment> findAll(Pageable pageable);
    
    long countByDiscussion(Discussion discussion);
    
    long countByUser(User user);
    
    long countByDiscussionAndUser(Discussion discussion, User user);
} 