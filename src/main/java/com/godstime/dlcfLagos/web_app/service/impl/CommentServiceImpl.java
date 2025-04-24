package com.godstime.dlcfLagos.web_app.service.impl;

import com.godstime.dlcfLagos.web_app.dto.CommentDTO;
import com.godstime.dlcfLagos.web_app.exception.ResourceNotFoundException;
import com.godstime.dlcfLagos.web_app.exception.UnauthorizedException;
import com.godstime.dlcfLagos.web_app.model.Comment;
import com.godstime.dlcfLagos.web_app.model.Discussion;
import com.godstime.dlcfLagos.web_app.model.User;
import com.godstime.dlcfLagos.web_app.repository.CommentRepository;
import com.godstime.dlcfLagos.web_app.repository.DiscussionRepository;
import com.godstime.dlcfLagos.web_app.repository.UserRepository;
import com.godstime.dlcfLagos.web_app.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DiscussionRepository discussionRepository;

    @Override
    public Comment createComment(CommentDTO commentDTO, User user, Discussion discussion) {
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }
        if (discussion == null) {
            throw new ResourceNotFoundException("Discussion not found");
        }

        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setUser(user);
        comment.setDiscussion(discussion);
        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(Long id, CommentDTO commentDTO, User user) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + id));
        
        if (!comment.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException("You are not authorized to update this comment");
        }
        
        comment.setContent(commentDTO.getContent());
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long id, User user) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + id));
        
        if (!comment.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException("You are not authorized to delete this comment");
        }
        
        commentRepository.delete(comment);
    }

    @Override
    public Comment getCommentById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + id));
    }

    @Override
    public List<Comment> getCommentsByDiscussion(Discussion discussion) {
        if (discussion == null) {
            throw new ResourceNotFoundException("Discussion not found");
        }
        return commentRepository.findByDiscussion(discussion);
    }

    @Override
    public List<Comment> getCommentsByUser(User user) {
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }
        return commentRepository.findByUser(user);
    }

    @Override
    public List<Comment> getCommentsByDiscussionAndUser(Discussion discussion, User user) {
        if (discussion == null) {
            throw new ResourceNotFoundException("Discussion not found");
        }
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }
        return commentRepository.findByDiscussionAndUser(discussion, user);
    }

    @Override
    public List<Comment> getCommentsByDiscussionOrderByCreatedAtDesc(Discussion discussion) {
        if (discussion == null) {
            throw new ResourceNotFoundException("Discussion not found");
        }
        return commentRepository.findByDiscussionOrderByCreatedAtDesc(discussion);
    }

    @Override
    public List<Comment> getCommentsByDiscussionOrderByLikesCountDesc(Discussion discussion) {
        if (discussion == null) {
            throw new ResourceNotFoundException("Discussion not found");
        }
        return commentRepository.findByDiscussionOrderByLikesCountDesc(discussion);
    }

    @Override
    public List<Comment> getEditedComments() {
        return commentRepository.findByIsEdited(true);
    }

    @Override
    public Page<Comment> getAllComments(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    @Override
    public long countCommentsByDiscussion(Discussion discussion) {
        if (discussion == null) {
            throw new ResourceNotFoundException("Discussion not found");
        }
        return commentRepository.countByDiscussion(discussion);
    }

    @Override
    public long countCommentsByUser(User user) {
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }
        return commentRepository.countByUser(user);
    }

    @Override
    public long countCommentsByDiscussionAndUser(Discussion discussion, User user) {
        if (discussion == null) {
            throw new ResourceNotFoundException("Discussion not found");
        }
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }
        return commentRepository.countByDiscussionAndUser(discussion, user);
    }

    @Override
    public boolean existsById(Long id) {
        return commentRepository.existsById(id);
    }

    @Override
    public Comment likeComment(Long id, User user) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + id));
        
        comment.setLikesCount(comment.getLikesCount() + 1);
        return commentRepository.save(comment);
    }

    @Override
    public Comment unlikeComment(Long id, User user) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + id));
        
        if (comment.getLikesCount() > 0) {
            comment.setLikesCount(comment.getLikesCount() - 1);
        }
        
        return commentRepository.save(comment);
    }
} 