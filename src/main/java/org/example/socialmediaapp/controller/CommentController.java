package org.example.socialmediaapp.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.socialmediaapp.dto.CommentCreate;
import org.example.socialmediaapp.dto.CommentPreview;
import org.example.socialmediaapp.dto.PageResponse;
import org.example.socialmediaapp.dto.PageResponseWithLinks;
import org.example.socialmediaapp.hateoas.CommentPreviewModelAssembler;
import org.example.socialmediaapp.hateoas.PaginationLinks;
import org.example.socialmediaapp.mapper.CommentMapper;
import org.example.socialmediaapp.model.Comment;
import org.example.socialmediaapp.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Tag(name = "Comments", description = "Operations related to comments")
@RestController
@RequestMapping(path = "/api/v1/comments", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentPreviewModelAssembler commentPreviewAssembler;


    @GetMapping
    public ResponseEntity<PageResponseWithLinks<EntityModel<CommentPreview>>> getAllComments(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,

                                                                                             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,

                                                                                             @PageableDefault(sort = "publishDate", direction = Sort.Direction.DESC, size = 10) Pageable pageable) {
        Page<CommentPreview> page;

        if (from != null && to != null) {
            page = commentService.filterByDateRange(from, to, pageable);
        } else {
            page = commentService.getAllComments(pageable);
        }

        var content = page.getContent().stream().map(commentPreviewAssembler::toModel).toList();

        PageResponse<EntityModel<CommentPreview>> pageResponse = new PageResponse<>(content, page.getTotalElements(), page.getNumber(), page.getSize());

        Map<String, String> filters = new HashMap<>();
        if (from != null) {
            filters.put("from", from.toString());
        }
        if (to != null) {
            filters.put("to", to.toString());
        }

        Map<String, Link> links = PaginationLinks.create(page.getNumber(), page.getSize(), page.getTotalElements(), CommentController.class, filters);

        PageResponseWithLinks<EntityModel<CommentPreview>> body = new PageResponseWithLinks<>(pageResponse, links);

        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS).cachePublic()).body(body);
    }

    @PostMapping
    public ResponseEntity<EntityModel<CommentPreview>> createComment(@RequestBody @Valid CommentCreate commentCreate) {

        Comment created = commentService.createComment(commentCreate);

        CommentPreview dto = CommentMapper.toCommentPreview(created);

        EntityModel<CommentPreview> model = commentPreviewAssembler.toModel(dto);

        URI location = linkTo(CommentController.class).slash(dto.id()).toUri();

        return ResponseEntity.created(location).body(model);
    }


    @DeleteMapping("/{id}")
    public String deleteComment(@PathVariable String id) {
        return commentService.deleteComment(id);
    }
}
