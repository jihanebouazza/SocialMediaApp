package org.example.socialmediaapp.hateoas;

import org.example.socialmediaapp.controller.PostController;
import org.example.socialmediaapp.controller.TagController;
import org.example.socialmediaapp.controller.UserController;
import org.example.socialmediaapp.dto.PostFull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PostModelAssembler {

    public EntityModel<PostFull> toModel(PostFull post) {
        EntityModel<PostFull> model = EntityModel.of(post);

        model.add(linkTo(PostController.class).slash(post.id()).withSelfRel());

        model.add(linkTo(PostController.class).withRel("posts"));

        if (post.user() != null) {
            model.add(linkTo(UserController.class).slash(post.user().id()).withRel("user"));
        }

        model.add(linkTo(PostController.class).slash(post.id()).slash("comments").withRel("comments"));

        if (post.tags() != null) {
            post.tags().forEach(tag -> model.add(linkTo(TagController.class).slash(tag).slash("posts").withRel("tag-posts")));
        }

        return model;
    }
}
