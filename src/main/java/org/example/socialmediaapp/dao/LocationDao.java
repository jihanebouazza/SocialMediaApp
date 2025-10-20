package org.example.socialmediaapp.dao;

import org.example.socialmediaapp.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationDao extends JpaRepository<Location,Integer> {
}
