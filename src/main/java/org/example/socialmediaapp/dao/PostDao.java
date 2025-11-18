package org.example.socialmediaapp.dao;

import org.example.socialmediaapp.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface PostDao extends JpaRepository<Post, String>, JpaSpecificationExecutor<Post> {
    Page<Post> findByUserId(String userId, Pageable pageable);

    @Query("select p from Post p where :tag member of p.tags")
    Page<Post> findByTag(String tag, Pageable pageable);

    Page<Post> findByTextContainingIgnoreCase(String q, Pageable pageable);
}

