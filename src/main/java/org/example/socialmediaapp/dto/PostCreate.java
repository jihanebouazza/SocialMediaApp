package org.example.socialmediaapp.dto;

import java.util.List;

public record PostCreate(
        String text,
        String image,
        List<String> tags,
        String user
) {}