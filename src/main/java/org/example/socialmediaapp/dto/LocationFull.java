package org.example.socialmediaapp.dto;

public record LocationFull(
        String street,
        String city,
        String state,
        String country,
        String timezone
) {
}
