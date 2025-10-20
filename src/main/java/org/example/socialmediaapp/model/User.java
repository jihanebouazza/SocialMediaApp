package org.example.socialmediaapp.model;


import jakarta.persistence.*;
import org.example.socialmediaapp.model.Location;

@Entity

@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private String id;
    private String title;
    private String firstName;
    private String lastName;
    private String email;
    private String dateOfBirth;
    private String registerDate;
    private String phone;
    private String picture;


    private Location location;

    public User(){}
    public User(String string, String mr, String john, String doe, String mail, Location location) {}

    // Getters et setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getRegisterDate() { return registerDate; }
    public void setRegisterDate(String registerDate) { this.registerDate = registerDate; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPicture() { return picture; }
    public void setPicture(String picture) { this.picture = picture; }

}