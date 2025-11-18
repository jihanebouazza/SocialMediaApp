package org.example.socialmediaapp.service;

import jakarta.transaction.Transactional;
import org.example.socialmediaapp.dao.UserDao;
import org.example.socialmediaapp.dto.UserCreate;
import org.example.socialmediaapp.exception.ResourceNotFoundException;
import org.example.socialmediaapp.model.Location;
import org.example.socialmediaapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
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
        return userDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", id));
    }

    public User createUser(UserCreate user) {
        User newUser = new User();
        newUser.setTitle(user.title());
        newUser.setFirstName(user.firstName());
        newUser.setLastName(user.lastName());
        newUser.setEmail(user.email());
        newUser.setDateOfBirth(user.dateOfBirth());
        newUser.setPhone(user.phone());
        newUser.setPicture(user.picture());

        if (user.location() != null) {
            Location newLoc = new Location();
            newLoc.setStreet(user.location().street());
            newLoc.setCity(user.location().city());
            newLoc.setState(user.location().state());
            newLoc.setCountry(user.location().country());
            newLoc.setTimezone(user.location().timezone());


            newUser.setLocation(newLoc);
        }

        return userDao.save(newUser);
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
            throw new ResourceNotFoundException("User", id);
        }

        userDao.deleteById(id);
        return id;
    }

    public Page<User> searchByEmail(String email, Pageable pageable) {
        return userDao.findByEmailContainingIgnoreCase(email.trim(), pageable);
    }

    public Page<User> filterByTitle(String title, Pageable pageable) {
      return userDao.findByTitleIgnoreCase(title.trim(), pageable);
    }
}
