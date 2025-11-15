package org.example.socialmediaapp.mapper;

import org.example.socialmediaapp.dto.UserPreview;
import org.example.socialmediaapp.model.User;

public class UserMapper {
    public static UserPreview toUserPreview(User user) {
        if (user == null) return null;
        return new UserPreview(user.getId(), user.getTitle(), user.getFirstName(), user.getLastName(), user.getPicture());
    }
}
