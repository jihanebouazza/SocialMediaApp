package org.example.socialmediaapp.hateoas;

import org.example.socialmediaapp.controller.UserController;
import org.example.socialmediaapp.dto.UserFull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class UserModelAssembler {
    public EntityModel<UserFull> toModel(UserFull user) {

        EntityModel<UserFull> model = EntityModel.of(user);

        // Self link
        model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUser(user.id())).withSelfRel());

        // Link to all users
        model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getAllUsers(null, null, null)).withRel("users"));

        // Link to user's posts
        model.add(Link.of("/api/v1/users/" + user.id() + "/posts").withRel("posts"));

        // Link to user's comments
        model.add(Link.of("/api/v1/users/" + user.id() + "/comments").withRel("comments"));

        return model;
    }
}
