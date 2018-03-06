package com.dummy.commentsApp.repository;

import com.dummy.commentsApp.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByAuthor(String author);
}
