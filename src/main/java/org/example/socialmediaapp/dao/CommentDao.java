package org.example.socialmediaapp.dao;

import org.example.socialmediaapp.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface CommentDao extends JpaRepository<Comment, String> {
    Page<Comment> findByPostId(String postId, Pageable pageable);

    Page<Comment> findByUserId(String userId, Pageable pageable);

    Page<Comment> findByPublishDateBetween(
            LocalDateTime from,
            LocalDateTime to,
            Pageable pageable
    );

}
