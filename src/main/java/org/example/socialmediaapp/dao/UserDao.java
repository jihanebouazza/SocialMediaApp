package org.example.socialmediaapp.dao;

import org.example.socialmediaapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, String> {

}