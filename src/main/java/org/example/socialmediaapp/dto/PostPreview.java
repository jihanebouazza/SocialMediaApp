package org.example.socialmediaapp.dto;

import java.util.List;
import java.time.LocalDateTime;

public record PostPreview(
        String id,
        String text,
        String image,
        int likes,
        List<String> tags,
        LocalDateTime publishDate,
        UserPreview user
) {}