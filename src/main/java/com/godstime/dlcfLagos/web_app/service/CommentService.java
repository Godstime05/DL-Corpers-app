package com.godstime.dlcfLagos.web_app.service;

import com.godstime.dlcfLagos.web_app.dto.CommentDTO;
import com.godstime.dlcfLagos.web_app.model.Comment;
import com.godstime.dlcfLagos.web_app.model.Discussion;
import com.godstime.dlcfLagos.web_app.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {
    
    Comment createComment(CommentDTO commentDTO, User user, Discussion discussion);
    
    Comment updateComment(Long id, CommentDTO commentDTO, User user);
    
    void deleteComment(Long id, User user);
    
    Comment getCommentById(Long id);
    
    List<Comment> getCommentsByDiscussion(Discussion discussion);
    
    List<Comment> getCommentsByUser(User user);
    
    List<Comment> getCommentsByDiscussionAndUser(Discussion discussion, User user);
    
    List<Comment> getCommentsByDiscussionOrderByCreatedAtDesc(Discussion discussion);
    
    List<Comment> getCommentsByDiscussionOrderByLikesCountDesc(Discussion discussion);
    
    List<Comment> getEditedComments();
    
    Page<Comment> getAllComments(Pageable pageable);
    
    long countCommentsByDiscussion(Discussion discussion);
    
    long countCommentsByUser(User user);
    
    long countCommentsByDiscussionAndUser(Discussion discussion, User user);
    
    boolean existsById(Long id);
    
    Comment likeComment(Long id, User user);
    
    Comment unlikeComment(Long id, User user);
} 