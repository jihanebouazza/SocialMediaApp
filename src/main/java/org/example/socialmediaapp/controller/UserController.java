package org.example.socialmediaapp.controller;


import jakarta.validation.Valid;
import org.example.socialmediaapp.dto.CommentPreview;
import org.example.socialmediaapp.dto.PageResponse;
import org.example.socialmediaapp.dto.PostPreview;
import org.example.socialmediaapp.dto.UserCreate;
import org.example.socialmediaapp.model.User;
import org.example.socialmediaapp.service.CommentService;
import org.example.socialmediaapp.service.PostService;
import org.example.socialmediaapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @GetMapping
    public PageResponse<User> getAllUsers(@PageableDefault(sort = "registerDate", direction = Sort.Direction.DESC, size = 10) Pageable pageable) {
        Page<User> page = userService.getAllUsers(pageable);

        return new PageResponse<>(
                page.getContent(),
                page.getTotalElements(),
                page.getNumber(),
                page.getSize()
        );
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public User createUser(@RequestBody @Valid UserCreate user) {
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable String id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable String id) {
        return userService.deleteUser(id);
    }

    @GetMapping("/{id}/posts")
    public PageResponse<PostPreview> getPostsByUser(@PathVariable String id, @PageableDefault(sort = "publishDate", direction = Sort.Direction.DESC, size = 10) Pageable pageable) {
        Page<PostPreview> page = postService.getPostsByUser(id, pageable);

        return new PageResponse<>(
                page.getContent(),
                page.getTotalElements(),
                page.getNumber(),
                page.getSize()
        );
    }

    @GetMapping("/{id}/comments")
    public PageResponse<CommentPreview> getCommentsByUser(@PathVariable String id, @PageableDefault(sort = "publishDate", direction = Sort.Direction.DESC, size = 10) Pageable pageable) {
        Page<CommentPreview> page = commentService.getCommentsByUser(id, pageable);

        return new PageResponse<>(
                page.getContent(),
                page.getTotalElements(),
                page.getNumber(),
                page.getSize()
        );
    }
}