package org.example.socialmediaapp.hateoas;

import org.example.socialmediaapp.controller.PostController;
import org.example.socialmediaapp.controller.TagController;
import org.example.socialmediaapp.controller.UserController;
import org.example.socialmediaapp.dto.PostPreview;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class PostPreviewModelAssembler {

    public EntityModel<PostPreview> toModel(PostPreview post) {
        EntityModel<PostPreview> model = EntityModel.of(post);

        model.add(linkTo(PostController.class).slash(post.id()).withSelfRel());

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
