package org.example.socialmediaapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LocationCreate(
        @Size(min = 5, max = 100) String street,
        @Size(min = 2, max = 30) String city,
        @Size(min = 2, max = 30) String state,
        @Size(min = 2, max = 30) String country,
        String timezone
) {}