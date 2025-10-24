package org.example.socialmediaapp.controller;


import org.example.socialmediaapp.dao.CommentDao;
import org.example.socialmediaapp.model.Comment;
import org.example.socialmediaapp.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentDao commentDao;

    @GetMapping
    public List<Comment> getAllLocations() {
        return commentDao.findAll();
    }
}
