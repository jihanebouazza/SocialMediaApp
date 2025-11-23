package org.example.socialmediaapp.dto;

import org.springframework.hateoas.Link;

import java.util.Map;

public record PageResponseWithLinks<T>(
        PageResponse<T> page,
        Map<String, Link> _links
) {
}
