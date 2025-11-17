package org.example.socialmediaapp.controller;

import jakarta.validation.Valid;
import org.example.socialmediaapp.dao.PostDao;
import org.example.socialmediaapp.dto.CommentPreview;
import org.example.socialmediaapp.dto.PageResponse;
import org.example.socialmediaapp.dto.PostCreate;
import org.example.socialmediaapp.dto.PostPreview;
import org.example.socialmediaapp.model.Comment;
import org.example.socialmediaapp.model.Post;
import org.example.socialmediaapp.service.CommentService;
import org.example.socialmediaapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;

    @GetMapping
    public PageResponse<PostPreview> getAllPosts(@PageableDefault(sort = "publishDate", direction = Sort.Direction.DESC, size = 10) Pageable pageable) {
        Page<PostPreview> page = postService.getAllPostsPreview(pageable);

        return new PageResponse<>(page.getContent(), page.getTotalElements(), page.getNumber(), page.getSize());
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable String id) {
        return postService.getPostById(id);
    }

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
