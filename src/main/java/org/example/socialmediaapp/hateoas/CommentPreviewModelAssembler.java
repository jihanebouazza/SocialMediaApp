package org.example.socialmediaapp.hateoas;

import org.example.socialmediaapp.controller.CommentController;
import org.example.socialmediaapp.controller.PostController;
import org.example.socialmediaapp.controller.UserController;
import org.example.socialmediaapp.dto.CommentPreview;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class CommentPreviewModelAssembler {
    public EntityModel<CommentPreview> toModel(CommentPreview comment) {
        EntityModel<CommentPreview> model = EntityModel.of(comment);

        model.add(linkTo(CommentController.class).slash(comment.id()).withSelfRel());

        if (comment.user() != null) {
            model.add(linkTo(UserController.class).slash(comment.user().id()).withRel("user"));
        }

        if (comment.post() != null) {
            model.add(linkTo(PostController.class).slash(comment.post()).withRel("post"));
        }

        return model;
    }
}
