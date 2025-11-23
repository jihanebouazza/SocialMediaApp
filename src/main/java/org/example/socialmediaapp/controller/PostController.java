package org.example.socialmediaapp.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.socialmediaapp.dto.*;
import org.example.socialmediaapp.hateoas.CommentPreviewModelAssembler;
import org.example.socialmediaapp.hateoas.PaginationLinks;
import org.example.socialmediaapp.hateoas.PostModelAssembler;
import org.example.socialmediaapp.hateoas.PostPreviewModelAssembler;
import org.example.socialmediaapp.mapper.PostMapper;
import org.example.socialmediaapp.model.Post;
import org.example.socialmediaapp.service.CommentService;
import org.example.socialmediaapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Tag(name = "Posts", description = "Operations related to posts")
@RestController
@RequestMapping(path = "/api/v1/posts", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;

    @Autowired
    private PostPreviewModelAssembler postPreviewAssembler;

    @Autowired
    private PostModelAssembler postModelAssembler;

    @Autowired
    private CommentPreviewModelAssembler commentPreviewAssembler;


    @GetMapping
    public ResponseEntity<PageResponseWithLinks<EntityModel<PostPreview>>> getPosts(@RequestParam(required = false) String q, @PageableDefault(sort = "publishDate", direction = Sort.Direction.DESC, size = 10) Pageable pageable) {

        Page<PostPreview> page;

        if (q != null && !q.isBlank()) {
            page = postService.searchPosts(q, pageable);
        } else {
            page = postService.getAllPosts(pageable);
        }

        var content = page.getContent().stream().map(postPreviewAssembler::toModel).toList();

        PageResponse<EntityModel<PostPreview>> pageResponse = new PageResponse<>(content, page.getTotalElements(), page.getNumber(), page.getSize());

        Map<String, String> filters = new HashMap<>();
        filters.put("q", q);

        Map<String, Link> links = PaginationLinks.create(page.getNumber(), page.getSize(), page.getTotalElements(), PostController.class, filters);

        PageResponseWithLinks<EntityModel<PostPreview>> body = new PageResponseWithLinks<>(pageResponse, links);

        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS).cachePublic()).body(body);
    }


    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<PostFull>> getPostById(@PathVariable String id) {
        PostFull post = postService.getPostById(id);
        return ResponseEntity.ok(postModelAssembler.toModel(post));
    }

    /*
    @GetMapping(
            value = "/{id}",
            produces = "application/vnd.users.v1+json"
    )
    public Map<String, Object> getPostV1(@PathVariable String id) {
        Map<String, Object> body = new HashMap<>();
        body.put("version", "v1");
        body.put("id", id);
        body.put("message", "This is version 1 of the post API");
        body.put("data", postService.getPostById(id));
        return body;
    }

    @GetMapping(
            value = "/{id}",
            produces = "application/vnd.users.v2+json"
    )
    public Map<String, Object> getPostV2(@PathVariable String id) {
        Map<String, Object> body = new HashMap<>();
        body.put("version", "v2");
        body.put("id", id);
        body.put("message", "This is version 2 of the post API");
        body.put("data", postService.getPostById(id));
        return body;
    }
    */
    @PostMapping
    public ResponseEntity<EntityModel<PostFull>> createPost(@RequestBody @Valid PostCreate postCreate) {
        Post created = postService.createPost(postCreate);
        PostFull dto = PostMapper.toPostFull(created); // you already have PostMapper

        EntityModel<PostFull> model = postModelAssembler.toModel(dto);

        URI location = linkTo(PostController.class).slash(dto.id()).toUri();

        return ResponseEntity.created(location).body(model);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<PostFull>> updatePost(@PathVariable String id, @RequestBody Post postDetails) {

        Post updated = postService.updatePost(id, postDetails);
        PostFull dto = PostMapper.toPostFull(updated);

        return ResponseEntity.ok(postModelAssembler.toModel(dto));
    }

    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable String id) {
        return postService.deletePost(id);
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<PageResponseWithLinks<EntityModel<CommentPreview>>> getCommentsByPost(@PathVariable String id, @PageableDefault(sort = "publishDate", direction = Sort.Direction.DESC, size = 10) Pageable pageable) {

        Page<CommentPreview> page = commentService.getCommentsByPost(id, pageable);

        var content = page.getContent().stream().map(commentPreviewAssembler::toModel).toList();

        PageResponse<EntityModel<CommentPreview>> pageResponse = new PageResponse<>(content, page.getTotalElements(), page.getNumber(), page.getSize());

        String baseUrl = linkTo(PostController.class).slash(id).slash("comments").toUri().toString();

        Map<String, Link> links = PaginationLinks.createForBaseUrl(page, baseUrl);

        PageResponseWithLinks<EntityModel<CommentPreview>> body = new PageResponseWithLinks<>(pageResponse, links);

        return ResponseEntity.ok(body);
    }


}
