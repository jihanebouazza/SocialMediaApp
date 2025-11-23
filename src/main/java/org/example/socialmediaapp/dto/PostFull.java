package org.example.socialmediaapp.dto;

import java.time.LocalDateTime;
import java.util.List;

public record PostFull(
        String id,
        String text,
        String image,
        int likes,
        String link,
        List<String> tags,
        LocalDateTime publishDate,
        UserPreview user) {
}
