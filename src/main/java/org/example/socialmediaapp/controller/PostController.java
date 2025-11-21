package org.example.socialmediaapp.controller;

import jakarta.validation.Valid;
import org.example.socialmediaapp.dto.CommentPreview;
import org.example.socialmediaapp.dto.PageResponse;
import org.example.socialmediaapp.dto.PostCreate;
import org.example.socialmediaapp.dto.PostPreview;
import org.example.socialmediaapp.model.Post;
import org.example.socialmediaapp.service.CommentService;
import org.example.socialmediaapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/posts", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;

    @GetMapping
    public PageResponse<PostPreview> getPosts(
            @RequestParam(required = false) String q,
            @PageableDefault(sort = "publishDate",
                    direction = Sort.Direction.DESC,
                    size = 10) Pageable pageable) {

        Page<PostPreview> page;

        if (q != null && !q.isBlank()) {
            page = postService.searchPosts(q, pageable);
        } else {
            page = postService.getAllPostsPreview(pageable);
        }

        return new PageResponse<>(
                page.getContent(),
                page.getTotalElements(),
                page.getNumber(),
                page.getSize()
        );
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable String id) {
        return postService.getPostById(id);
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
    public Post createPost(@RequestBody @Valid PostCreate post) {
        return postService.createPost(post);
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable String id, @RequestBody Post post) {
        return postService.updatePost(id, post);
    }

    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable String id) {
        return postService.deletePost(id);
    }

    @GetMapping("{id}/comments")
    public PageResponse<CommentPreview> getCommentsByPost(@PathVariable String id, @PageableDefault(sort = "publishDate", direction = Sort.Direction.DESC, size = 10) Pageable pageable) {

        Page<CommentPreview> page = commentService.getCommentsByPost(id, pageable);

        return new PageResponse<>(page.getContent(), page.getTotalElements(), page.getNumber(), page.getSize());
    }

}
