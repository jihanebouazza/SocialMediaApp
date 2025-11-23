package org.example.socialmediaapp.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.socialmediaapp.dao.PostDao;
import org.example.socialmediaapp.dto.PageResponse;
import org.example.socialmediaapp.dto.PageResponseWithLinks;
import org.example.socialmediaapp.dto.PostFull;
import org.example.socialmediaapp.dto.PostPreview;
import org.example.socialmediaapp.hateoas.PaginationLinks;
import org.example.socialmediaapp.hateoas.PostPreviewModelAssembler;
import org.example.socialmediaapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Tag(name = "Tags", description = "Operations related to tags")
@RestController
@RequestMapping(path = "/api/v1/tags", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class TagController {
    @Autowired
    private PostService postService;

    @Autowired
    private PostDao postDao;

    @Autowired
    private PostPreviewModelAssembler postPreviewAssembler;


    @GetMapping
    public List<String> getAllTags() {
        return postDao.findAll().stream().flatMap(post -> post.getTags().stream()).distinct().sorted().toList();
    }

    @GetMapping("/{tag}/posts")
    public ResponseEntity<PageResponseWithLinks<EntityModel<PostPreview>>> getPostsByTag(@PathVariable String tag, @PageableDefault(sort = "publishDate", direction = Sort.Direction.DESC, size = 10) Pageable pageable) {

        Page<PostPreview> page = postService.getPostsByTag(tag, pageable);

        var content = page.getContent().stream().map(postPreviewAssembler::toModel).toList();

        PageResponse<EntityModel<PostPreview>> pageResponse = new PageResponse<>(content, page.getTotalElements(), page.getNumber(), page.getSize());

        String baseUrl = linkTo(TagController.class).slash(tag).slash("posts").toUri().toString();

        Map<String, Link> links = PaginationLinks.createForBaseUrl(page, baseUrl);

        PageResponseWithLinks<EntityModel<PostPreview>> body = new PageResponseWithLinks<>(pageResponse, links);

        return ResponseEntity.ok(body);
    }


}

