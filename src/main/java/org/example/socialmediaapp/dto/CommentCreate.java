package org.example.socialmediaapp.dto;

import jakarta.validation.constraints.NotBlank;

public record CommentCreate(
        @NotBlank String message,
        @NotBlank String user,
        @NotBlank String post
) {}
