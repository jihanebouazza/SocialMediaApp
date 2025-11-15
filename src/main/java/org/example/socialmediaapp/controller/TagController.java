package org.example.socialmediaapp.controller;

import org.example.socialmediaapp.dao.PostDao;
import org.example.socialmediaapp.dto.PageResponse;
import org.example.socialmediaapp.dto.PostPreview;
import org.example.socialmediaapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    @Autowired
    private PostService postService;

    @Autowired
    private PostDao postDao;

    @GetMapping
    public List<String> getAllTags() {
        return postDao.findAll().stream()
                .flatMap(post -> post.getTags().stream())
                .distinct()
                .sorted()
                .toList();
    }

    @GetMapping("/{tag}/posts")
    public PageResponse<PostPreview> getPostsByTag(
            @PathVariable String tag,
            @PageableDefault(
                    sort = "publishDate",
                    direction = Sort.Direction.DESC,
                    size = 10
            ) Pageable pageable
    ) {
        Page<PostPreview> page = postService.getPostsByTag(tag, pageable);

        return new PageResponse<>(
                page.getContent(),
                page.getTotalElements(),
                page.getNumber(),
                page.getSize()
        );
    }
}

