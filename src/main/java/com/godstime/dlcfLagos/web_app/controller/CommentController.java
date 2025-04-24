package com.godstime.dlcfLagos.web_app.controller;

import com.godstime.dlcfLagos.web_app.dto.CommentDTO;
import com.godstime.dlcfLagos.web_app.dto.UserDTO;
import com.godstime.dlcfLagos.web_app.exception.ResourceNotFoundException;
import com.godstime.dlcfLagos.web_app.exception.UnauthorizedException;
import com.godstime.dlcfLagos.web_app.model.Comment;
import com.godstime.dlcfLagos.web_app.model.Discussion;
import com.godstime.dlcfLagos.web_app.model.User;
import com.godstime.dlcfLagos.web_app.service.CommentService;
import com.godstime.dlcfLagos.web_app.service.DiscussionService;
import com.godstime.dlcfLagos.web_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private DiscussionService discussionService;

    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody CommentDTO commentDTO, Authentication authentication) {
        try {
            User user = userService.findUserByEmail(authentication.getName());
            Discussion discussion = discussionService.findDiscussionById(commentDTO.getDiscussionId());
            Comment comment = commentService.createComment(commentDTO, user, discussion);
            return ResponseEntity.ok(comment);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating comment: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long id, @RequestBody CommentDTO commentDTO, Authentication authentication) {
        try {
           // User user = userService.getUserByEmail(authentication.getName());
            User user = userService.findUserByEmail(authentication.getName());
            Comment comment = commentService.updateComment(id, commentDTO, user);
            return ResponseEntity.ok(comment);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the comment");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id, Authentication authentication) {
        try {
            User user = userService.findUserByEmail(authentication.getName());
            commentService.deleteComment(id, user);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the comment");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCommentById(@PathVariable Long id) {
        try {
            Comment comment = commentService.getCommentById(id);
            return ResponseEntity.ok(comment);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while retrieving the comment");
        }
    }

    @GetMapping("/discussion/{discussionId}")
    public ResponseEntity<?> getCommentsByDiscussion(@PathVariable Long discussionId) {
        try {
            Discussion discussion = discussionService.findDiscussionById(discussionId);
            List<Comment> comments = commentService.getCommentsByDiscussion(discussion);
            return ResponseEntity.ok(comments);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while retrieving comments");
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getCommentsByUser(@PathVariable Long userId) {
        try {
            User user = userService.findUserById(userId);
            List<Comment> comments = commentService.getCommentsByUser(user);
            return ResponseEntity.ok(comments);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while retrieving comments");
        }
    }

    @GetMapping("/discussion/{discussionId}/user/{userId}")
    public ResponseEntity<?> getCommentsByDiscussionAndUser(
            @PathVariable Long discussionId,
            @PathVariable Long userId) {
        try {
            Discussion discussion = discussionService.findDiscussionById(discussionId);
            User user = userService.findUserById(userId);
            List<Comment> comments = commentService.getCommentsByDiscussionAndUser(discussion, user);
            return ResponseEntity.ok(comments);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while retrieving comments");
        }
    }

    @GetMapping("/discussion/{discussionId}/latest")
    public ResponseEntity<?> getCommentsByDiscussionOrderByCreatedAtDesc(@PathVariable Long discussionId) {
        try {
            Discussion discussion = discussionService.findDiscussionById(discussionId);
            List<Comment> comments = commentService.getCommentsByDiscussionOrderByCreatedAtDesc(discussion);
            return ResponseEntity.ok(comments);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while retrieving comments");
        }
    }

    @GetMapping("/discussion/{discussionId}/popular")
    public ResponseEntity<?> getCommentsByDiscussionOrderByLikesCountDesc(@PathVariable Long discussionId) {
        try {
            Discussion discussion = discussionService.findDiscussionById(discussionId);
            List<Comment> comments = commentService.getCommentsByDiscussionOrderByLikesCountDesc(discussion);
            return ResponseEntity.ok(comments);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while retrieving comments");
        }
    }

    @GetMapping("/edited")
    public ResponseEntity<?> getEditedComments() {
        try {
            List<Comment> comments = commentService.getEditedComments();
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while retrieving edited comments");
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllComments(Pageable pageable) {
        try {
            Page<Comment> comments = commentService.getAllComments(pageable);
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while retrieving comments");
        }
    }

    @GetMapping("/discussion/{discussionId}/count")
    public ResponseEntity<?> countCommentsByDiscussion(@PathVariable Long discussionId) {
        try {
            Discussion discussion = discussionService.findDiscussionById(discussionId);
            long count = commentService.countCommentsByDiscussion(discussion);
            return ResponseEntity.ok(count);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while counting comments");
        }
    }

    @GetMapping("/user/{userId}/count")
    public ResponseEntity<?> countCommentsByUser(@PathVariable Long userId) {
        try {
            User user = userService.findUserById(userId);
            long count = commentService.countCommentsByUser(user);
            return ResponseEntity.ok(count);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while counting comments");
        }
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<?> likeComment(@PathVariable Long id, Authentication authentication) {
        try {
            User user = userService.findUserByEmail(authentication.getName());
            Comment comment = commentService.likeComment(id, user);
            return ResponseEntity.ok(comment);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while liking the comment");
        }
    }

    @PostMapping("/{id}/unlike")
    public ResponseEntity<?> unlikeComment(@PathVariable Long id, Authentication authentication) {
        try {
            User user = userService.findUserByEmail(authentication.getName());
            Comment comment = commentService.unlikeComment(id, user);
            return ResponseEntity.ok(comment);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while unliking the comment");
        }
    }
} 