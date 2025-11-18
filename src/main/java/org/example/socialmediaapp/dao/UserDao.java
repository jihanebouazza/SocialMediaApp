package org.example.socialmediaapp.dao;

import org.example.socialmediaapp.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    Page<User> findByEmailContainingIgnoreCase(String email, Pageable pageable);
    Page<User> findByTitleIgnoreCase(String title, Pageable pageable);
}