package org.example.socialmediaapp.dto;

public record UserFull(
        String id,
        String title,
        String firstName,
        String lastName,
        String email,
        String dateOfBirth,
        String registerDate,
        String phone,
        String picture,
        LocationFull location
) {
}
