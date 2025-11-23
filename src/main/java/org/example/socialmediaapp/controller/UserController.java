package org.example.socialmediaapp.controller;


import jakarta.validation.Valid;
import org.example.socialmediaapp.dto.*;
import org.example.socialmediaapp.hateoas.UserModelAssembler;
import org.example.socialmediaapp.model.User;
import org.example.socialmediaapp.service.CommentService;
import org.example.socialmediaapp.service.PostService;
import org.example.socialmediaapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(path = "/api/v1/users", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserModelAssembler assembler;

    @GetMapping
    public ResponseEntity<PageResponse<EntityModel<UserFull>>> getAllUsers(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String email,
            @PageableDefault(sort = "registerDate", direction = Sort.Direction.DESC, size = 10)
            Pageable pageable
    ) {
        Page<UserFull> page;

        if (title != null && !title.isBlank()) {
            page = userService.filterByTitle(title, pageable);
        } else if (email != null && !email.isBlank()) {
            page = userService.searchByEmail(email, pageable);
        } else {
            page = userService.getAllUsers(pageable);
        }

        // 🔹 Add HATEOAS links to each user in the page
        var content = page.getContent().stream()
                .map(assembler::toModel)   // EntityModel<UserFull>
                .toList();

        PageResponse<EntityModel<UserFull>> body = new PageResponse<>(
                content,
                page.getTotalElements(),
                page.getNumber(),
                page.getSize()
        );

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS).cachePublic())
                .body(body);
    }


    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UserFull>> getUser(@PathVariable String id) {
        UserFull user = userService.getUserById(id);
        return ResponseEntity.ok(assembler.toModel(user));
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