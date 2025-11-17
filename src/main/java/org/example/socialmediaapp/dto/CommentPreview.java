package org.example.socialmediaapp.dto;

import java.time.LocalDateTime;

public record CommentPreview(
        String id,
        String message,
        String post,
        UserPreview user,
        LocalDateTime publishDate
) {}
