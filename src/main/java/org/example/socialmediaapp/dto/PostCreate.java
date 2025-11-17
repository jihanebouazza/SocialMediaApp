package org.example.socialmediaapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record PostCreate(
        @NotBlank @Size(min = 6, max = 1000) String text,
        String image,
        List<String> tags,
        @NotBlank String user
) {
}