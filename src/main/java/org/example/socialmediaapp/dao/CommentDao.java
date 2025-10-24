package org.example.socialmediaapp.dao;

import org.example.socialmediaapp.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentDao extends JpaRepository<Comment,String> {
}
