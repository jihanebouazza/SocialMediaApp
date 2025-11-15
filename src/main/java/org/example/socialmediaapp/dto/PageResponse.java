package org.example.socialmediaapp.dto;

import java.util.List;

public record PageResponse<T>(
        List<T> data,        // the list of items
        long total,          // total number of items in DB
        int page,            // current page number
        int limit           // number of items on this page
) {}
