package org.example.socialmediaapp.hateoas;

import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


import java.util.LinkedHashMap;
import java.util.Map;

public class PaginationLinks {
    public static Map<String, Link> create(int page, int size, long total, Class<?> controllerClass, Map<String, String> filters) {
        int lastPage = (int) Math.max(0, (total - 1) / size);

        Map<String, Link> links = new LinkedHashMap<>();

        // always present
        links.put("self", link(page, size, controllerClass, filters));
        links.put("first", link(0, size, controllerClass, filters));
        links.put("last", link(lastPage, size, controllerClass, filters));

        // only add if they exist
        if (page > 0) {
            links.put("prev", link(page - 1, size, controllerClass, filters));
        }
        if (page < lastPage) {
            links.put("next", link(page + 1, size, controllerClass, filters));
        }

        return links;
    }

    private static Link link(int page, int size, Class<?> controllerClass, Map<String, String> filters) {
        StringBuilder sb = new StringBuilder("?page=" + page + "&size=" + size);

        filters.forEach((k, v) -> {
            if (v != null && !v.isBlank()) {
                sb.append("&").append(k).append("=").append(v);
            }
        });

        return linkTo(controllerClass).slash(sb.toString()).withSelfRel();
    }
}
