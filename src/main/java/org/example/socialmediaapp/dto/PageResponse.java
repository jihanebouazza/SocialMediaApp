package org.example.socialmediaapp.dto;

import org.springframework.hateoas.Link;

import java.util.List;
import java.util.Map;

public record PageResponse<T>(
        List<T> data,        // the list of items
        long total,          // total number of items in DB
        int page,            // current page number
        int limit            // number of items on this page
) {}
