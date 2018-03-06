package com.dummy.commentsApp.controller;

import com.dummy.commentsApp.dto.JSONWrapper;
import com.dummy.commentsApp.model.Comment;
import com.dummy.commentsApp.service.CommentService;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/all")
    public List<Comment> getAllComments(@PathParam("author") String author) {
        return commentService.getAllComments(author);
    }

    @PostMapping
    public void createComment(@RequestBody JSONWrapper commentText) {
        commentService.createComment(commentText.getValue());
    }

    @PutMapping("/{id}")
    public void updateComment(@PathVariable("id") Long id, @RequestBody JSONWrapper commentText) {
        commentService.updateComment(id, commentText.getValue());
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable("id") Long id) {
        commentService.deleteComment(id);
    }
}
