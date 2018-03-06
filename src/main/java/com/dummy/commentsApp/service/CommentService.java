package com.dummy.commentsApp.service;

import com.dummy.commentsApp.model.Comment;
import com.dummy.commentsApp.repository.CommentRepository;
import com.dummy.commentsApp.util.SecurityUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getAllComments(String author) {
        if (author == null) {
            return commentRepository.findAll();
        } else {
            return commentRepository.findByAuthor(author);
        }
    }

    public void createComment(String commentText) {
        Comment comment = new Comment();
        comment.setText(commentText);
        comment.setAuthor(SecurityUtil.getLogginInUsername());
        commentRepository.save(comment);
    }

    public void updateComment(Long id, String value) {
        Comment comment = commentRepository.getOne(id);
        if (SecurityUtil.getLogginInUsername().equals(comment.getAuthor())) {
            comment.setText(value);
            commentRepository.save(comment);
        } else {
            throw new BadCredentialsException("Comment either doesn't exist or you are not the author of it");
        }
    }

    public void deleteComment(Long id) {
        Comment comment = commentRepository.getOne(id);
        if (SecurityUtil.getLogginInUsername().equals(comment.getAuthor())) {
            commentRepository.delete(comment);
        } else {
            throw new BadCredentialsException("Comment either doesn't exist or you are not the author of it");
        }
    }
}
