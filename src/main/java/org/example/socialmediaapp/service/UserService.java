package org.example.socialmediaapp.service;

import jakarta.transaction.Transactional;
import org.example.socialmediaapp.dao.UserDao;
import org.example.socialmediaapp.model.Location;
import org.example.socialmediaapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
@Transactional
public class UserService {
    @Autowired
    private UserDao userDao;

    public Page<User> getAllUsers(Pageable pageable) {
        return userDao.findAll(pageable);
    }


    public User getUserById(String id) {
        return userDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public User createUser(User user) {

        if (user.getFirstName() == null || user.getFirstName().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "firstName is required");
        }

        if (user.getLastName() == null || user.getLastName().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "lastName is required");
        }

        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email is required");
        }

        return userDao.save(user);
    }

    public User updateUser(String id, User userDetails) {
        User user = getUserById(id);
        if (userDetails.getEmail() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email cannot be updated");
        }

        user.setTitle(userDetails.getTitle());
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setPhone(userDetails.getPhone());
        user.setPicture(userDetails.getPicture());
        user.setDateOfBirth(userDetails.getDateOfBirth());

        if (userDetails.getLocation() != null) {
            Location reqLoc = userDetails.getLocation();

            Location existingLoc = user.getLocation();
            if (existingLoc == null) {
                existingLoc = new Location();
                user.setLocation(existingLoc);
            }

            existingLoc.setStreet(reqLoc.getStreet());
            existingLoc.setCity(reqLoc.getCity());
            existingLoc.setState(reqLoc.getState());
            existingLoc.setCountry(reqLoc.getCountry());
            existingLoc.setTimezone(reqLoc.getTimezone());
        }

        return userDao.save(user);
    }

    public String deleteUser(String id) {

        if (!userDao.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        userDao.deleteById(id);
        return id;
    }
}
