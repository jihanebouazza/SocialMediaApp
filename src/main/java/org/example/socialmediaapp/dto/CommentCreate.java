package org.example.socialmediaapp.dto;

import java.time.LocalDateTime;

public class CommentCreate {
    private String message;
    private String owner;
    private String post;

    public CommentCreate() {}
    public CommentCreate(String message, String owner, String post) {
        this.message = message;
        this.owner = owner;
        this.post = post;
    }
    public String getMessage() {return message;}
    public void setMessage(String message) {this.message = message;}
    public String getOwner() {return owner;}
    public void setOwner(String owner) {this.owner = owner;}
    public String getPost() {return post;}
    public void setPost(String post) {this.post = post;}
}
