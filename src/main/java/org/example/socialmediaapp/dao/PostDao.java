package org.example.socialmediaapp.dao;
import org.example.socialmediaapp.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostDao  extends JpaRepository<Post, Long > {
}

