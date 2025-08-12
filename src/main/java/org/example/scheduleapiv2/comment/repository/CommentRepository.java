package org.example.scheduleapiv2.comment.repository;

import org.example.scheduleapiv2.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
