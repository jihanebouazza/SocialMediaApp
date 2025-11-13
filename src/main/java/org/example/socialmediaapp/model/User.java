
package org.example.socialmediaapp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
        import org.example.socialmediaapp.model.Location;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;

@Entity

@Table(name = "users")
public class User {
    @Id
    @UuidGenerator
    private String id;
    private String title;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;
    private String dateOfBirth;
    private String registerDate = java.time.LocalDate.now().toString();
    private String phone;
    private String picture;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    @JsonManagedReference("user-location")
    private Location location;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference("user-post")
    private List <Post> posts;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonManagedReference("user-comment")
    private List<Comment> comments;


    public User(){}

    public User(String id, String title, String firstName, String lastName, String email, String dateOfBirth, String registerDate, String phone, String picture, Location location, List<Post> posts) {
        this.id = id;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.registerDate = registerDate;
        this.phone = phone;
        this.picture = picture;
        this.location = location;
        this.posts = posts;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public String getPhone() {
        return phone;
    }

    public String getPicture() {
        return picture;
    }

    public Location getLocation() {
        return location;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}