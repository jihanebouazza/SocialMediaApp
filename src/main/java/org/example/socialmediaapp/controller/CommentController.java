package org.example.socialmediaapp.controller;


import jakarta.validation.Valid;
import org.example.socialmediaapp.dto.CommentCreate;
import org.example.socialmediaapp.dto.CommentPreview;
import org.example.socialmediaapp.dto.PageResponse;
import org.example.socialmediaapp.model.Comment;
import org.example.socialmediaapp.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping(path = "/api/v1/comments", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping
    public ResponseEntity<PageResponse<CommentPreview>> getAllComments(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to, @PageableDefault(sort = "publishDate", direction = Sort.Direction.DESC, size = 10) Pageable pageable) {
        Page<CommentPreview> page;

        if (from != null && to != null) {
            page = commentService.filterByDateRange(from, to, pageable);
        } else {
            page = commentService.getAllComments(pageable);
        }

        PageResponse<CommentPreview> body = new PageResponse<>(page.getContent(), page.getTotalElements(), page.getNumber(), page.getSize());

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS).cachePublic())
                .body(body);
    }

    @PostMapping
    public Comment createComment(@RequestBody @Valid CommentCreate comment) {
        return commentService.createComment(comment);
    }


    @DeleteMapping("/{id}")
    public String deleteComment(@PathVariable String id) {
        return commentService.deleteComment(id);
    }
}
