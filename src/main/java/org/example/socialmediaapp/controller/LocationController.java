package org.example.socialmediaapp.controller;

import org.example.socialmediaapp.dao.LocationDao;
import org.example.socialmediaapp.model.Location;
import org.example.socialmediaapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class LocationController {
    @Autowired
    private LocationDao locationDao;

    @GetMapping(value = "/locations")
    public List<Location> getAllLocations() {
        return locationDao.findAll();
    }
}
