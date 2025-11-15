package org.example.socialmediaapp.dto;

public record UserPreview(
        String id,
        String title,
        String firstName,
        String lastName,
        String picture
) {}
