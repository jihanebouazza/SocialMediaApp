package org.example.socialmediaapp.dto;
import java.util.List;
import java.time.LocalDateTime;
public class PostPreview {
    private String id;
    private String text;
    private String image;
    private int likes;
    private List<String> tags;
    private LocalDateTime publishDate;
    private UserPreview owner;


    public PostPreview() {
    }
public PostPreview(String id, String text, String image, int likes,List<String> tags ,LocalDateTime publishDate, UserPreview owner) {
        this.id = id;
        this.text = text;
        this.image = image;
        this.likes = likes;
        this.tags = tags;
        this.publishDate = publishDate;
        this.owner = owner;

}
public String getId() {return id;}
    public void setId(String id) {this.id = id;}
    public String getText() {return text;}
    public void setText(String text) {this.text = text;}
    public String getImage() {return image;}
    public void setImage(String image) {this.image = image;}
    public int getLikes() {return likes;}
    public void setLikes(int likes) {this.likes = likes;}
    public List<String> getTags() {return tags;}
    public void setTags(List<String> tags) {this.tags = tags;}
    public LocalDateTime getPublishDate() {return publishDate;}
    public void setPublishDate(LocalDateTime publishDate) {this.publishDate = publishDate;}
    public UserPreview getOwner() {return owner;}
    public void setOwner(UserPreview owner) {this.owner = owner;}

}