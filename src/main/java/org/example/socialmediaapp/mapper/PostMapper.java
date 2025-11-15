package org.example.socialmediaapp.mapper;

import org.example.socialmediaapp.dto.PostPreview;
import org.example.socialmediaapp.model.Post;

public class PostMapper {
    public static PostPreview toPostPreview(Post post) {
        if (post == null) return null;
        return new PostPreview(post.getId(), previewText(post.getText()), post.getImage(), post.getLikes(), post.getTags(), post.getPublishDate(), UserMapper.toUserPreview(post.getUser()));
    }

    private static String previewText(String text) {
        if (text == null) return null;

        text = text.trim();
        if (text.length() <= 50) return text;

        return text.substring(0, 50) + "...";
    }
}
