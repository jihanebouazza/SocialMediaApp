package org.example.socialmediaapp.controller;

import org.example.socialmediaapp.dao.PostDao;
import org.example.socialmediaapp.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostDao postDao;

    @GetMapping
    public List<Post> getAllPosts() {
        return postDao.findAll();
    }
}
