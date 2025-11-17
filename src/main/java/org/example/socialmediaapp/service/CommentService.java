package org.example.socialmediaapp.service;

import jakarta.transaction.Transactional;
import org.example.socialmediaapp.dao.CommentDao;
import org.example.socialmediaapp.dto.CommentCreate;
import org.example.socialmediaapp.dto.CommentPreview;
import org.example.socialmediaapp.mapper.CommentMapper;
import org.example.socialmediaapp.model.Comment;
import org.example.socialmediaapp.model.Post;
import org.example.socialmediaapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class CommentService {
    @Autowired
    private CommentDao commentDao;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    public Page<CommentPreview> getAllComments(Pageable pageable) {
        Page<Comment> p = commentDao.findAll(pageable);
        return p.map(CommentMapper::toCommentPreview);
    }

    public Page<CommentPreview> getCommentsByPost(String postId, Pageable pageable) {
        Page<Comment> p = commentDao.findByPostId(postId, pageable);
        return p.map(CommentMapper::toCommentPreview);
    }

    public Page<CommentPreview> getCommentsByUser(String userId, Pageable pageable) {
        Page<Comment> p = commentDao.findByUserId(userId, pageable);
        return p.map(CommentMapper::toCommentPreview);
    }

    public Comment createComment(CommentCreate comment) {

        if (comment.user() == null || comment.user().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user_id is required");
        }

        if (comment.post() == null || comment.post().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "post_id is required");
        }

        User user = userService.getUserById(comment.user());
        Post post = postService.getPostById(comment.post());

        Comment newComment = new Comment();
        newComment.setMessage(comment.message().trim());
        newComment.setUser(user);
        newComment.setPost(post);

        return commentDao.save(newComment);

    }

    public String deleteComment(String id) {

        if (!commentDao.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        commentDao.deleteById(id);
        return id;
    }
}
