package org.example.socialmediaapp.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.socialmediaapp.dto.*;
import org.example.socialmediaapp.hateoas.CommentPreviewModelAssembler;
import org.example.socialmediaapp.hateoas.PaginationLinks;
import org.example.socialmediaapp.hateoas.PostPreviewModelAssembler;
import org.example.socialmediaapp.hateoas.UserModelAssembler;
import org.example.socialmediaapp.mapper.UserMapper;
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
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Tag(name = "Users", description = "Operations related to users")
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
    private UserModelAssembler userAssembler;

    @Autowired
    private PostPreviewModelAssembler postPreviewAssembler;

    @Autowired
    private CommentPreviewModelAssembler commentPreviewAssembler;

    @GetMapping
    public ResponseEntity<PageResponseWithLinks<EntityModel<UserFull>>> getAllUsers(@RequestParam(required = false) String title, @RequestParam(required = false) String email, @PageableDefault(sort = "registerDate", direction = Sort.Direction.DESC, size = 10) Pageable pageable) {
        Page<UserFull> page;

        if (title != null && !title.isBlank()) {
            page = userService.filterByTitle(title, pageable);
        } else if (email != null && !email.isBlank()) {
            page = userService.searchByEmail(email, pageable);
        } else {
            page = userService.getAllUsers(pageable);
        }

        var content = page.getContent().stream().map(userAssembler::toModel).toList();

        PageResponse<EntityModel<UserFull>> pageResponse = new PageResponse<>(content, page.getTotalElements(), page.getNumber(), page.getSize());

        Map<String, String> filters = new HashMap<>();
        filters.put("title", title);
        filters.put("email", email);

        Map<String, Link> links = PaginationLinks.create(page.getNumber(), page.getSize(), page.getTotalElements(), UserController.class, filters);

        PageResponseWithLinks<EntityModel<UserFull>> body = new PageResponseWithLinks<>(pageResponse, links);

        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS).cachePublic()).body(body);
    }


    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UserFull>> getUser(@PathVariable String id) {
        UserFull user = userService.getUserById(id);
        return ResponseEntity.ok(userAssembler.toModel(user));
    }

    @PostMapping
    public ResponseEntity<EntityModel<UserFull>> createUser(@RequestBody @Valid UserCreate user) {
        // 1. Create entity
        User created = userService.createUser(user);

        // 2. Map entity -> DTO
        UserFull dto = UserMapper.toUserFull(created);

        // 3. Build Location header using the HATEOAS self link
        URI location = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUser(created.getId())).toUri();

        // 4. Return 201 Created + HATEOAS body
        return ResponseEntity.created(location).body(userAssembler.toModel(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<UserFull>> updateUser(@PathVariable String id, @RequestBody User user) {
        // 1. Update entity
        User updated = userService.updateUser(id, user);

        // 2. Map entity -> DTO
        UserFull dto = UserMapper.toUserFull(updated);

        // 3. Return 200 OK + HATEOAS body
        return ResponseEntity.ok(userAssembler.toModel(dto));
    }


    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable String id) {
        return userService.deleteUser(id);
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<PageResponseWithLinks<EntityModel<PostPreview>>> getPostsByUser(@PathVariable String id, @PageableDefault(sort = "publishDate", direction = Sort.Direction.DESC, size = 10) Pageable pageable) {

        Page<PostPreview> page = postService.getPostsByUser(id, pageable);

        var content = page.getContent().stream().map(postPreviewAssembler::toModel).toList();

        PageResponse<EntityModel<PostPreview>> pageResponse = new PageResponse<>(content, page.getTotalElements(), page.getNumber(), page.getSize());

        String baseUrl = linkTo(UserController.class).slash(id).slash("posts").toUri().toString();

        Map<String, Link> links = PaginationLinks.createForBaseUrl(page, baseUrl);

        PageResponseWithLinks<EntityModel<PostPreview>> body = new PageResponseWithLinks<>(pageResponse, links);

        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<PageResponseWithLinks<EntityModel<CommentPreview>>> getCommentsByUser(@PathVariable String id, @PageableDefault(sort = "publishDate", direction = Sort.Direction.DESC, size = 10) Pageable pageable) {

        Page<CommentPreview> page = commentService.getCommentsByUser(id, pageable);

        var content = page.getContent().stream().map(commentPreviewAssembler::toModel).toList();

        PageResponse<EntityModel<CommentPreview>> pageResponse = new PageResponse<>(content, page.getTotalElements(), page.getNumber(), page.getSize());

        String baseUrl = linkTo(UserController.class).slash(id).slash("comments").toUri().toString();

        Map<String, Link> links = PaginationLinks.createForBaseUrl(page, baseUrl);

        PageResponseWithLinks<EntityModel<CommentPreview>> body = new PageResponseWithLinks<>(pageResponse, links);

        return ResponseEntity.ok(body);
    }


}