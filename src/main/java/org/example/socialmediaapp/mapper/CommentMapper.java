package org.example.socialmediaapp.mapper;

import org.example.socialmediaapp.dto.CommentPreview;
import org.example.socialmediaapp.dto.UserPreview;
import org.example.socialmediaapp.model.Comment;
import org.example.socialmediaapp.model.User;

public class CommentMapper {
    public static CommentPreview toCommentPreview(Comment comment) {
        if (comment == null) return null;
        return new CommentPreview(comment.getId(), comment.getMessage(), comment.getPost().getId(), UserMapper.toUserPreview(comment.getUser()), comment.getPublishDate());
    }
}
