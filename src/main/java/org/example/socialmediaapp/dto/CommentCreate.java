package org.example.socialmediaapp.dto;

public record CommentCreate(
        String message,
        String user,
        String post
) {}
