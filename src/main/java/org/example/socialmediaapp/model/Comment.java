package org.example.socialmediaapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity
public class Comment {
    @Id
    @UuidGenerator
    private String id;
    private String message;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference("user-comment")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonBackReference("post-comment")
    private Post post;
    private LocalDateTime publishDate = LocalDateTime.now();

    public Comment() {}

    public Comment(String message, String id, User user, LocalDateTime publishDate, Post post) {
        this.message = message;
        this.id = id;
        this.user = user;
        this.publishDate = publishDate;
        this.post = post;
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }

    public Post getPost() {
        return post;
    }

    public LocalDateTime getPublishDate() {
        return publishDate;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public void setPublishDate(LocalDateTime publishDate) {
        this.publishDate = publishDate;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
