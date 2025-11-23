package org.example.socialmediaapp.mapper;

import org.example.socialmediaapp.dto.LocationFull;
import org.example.socialmediaapp.dto.UserFull;
import org.example.socialmediaapp.dto.UserPreview;
import org.example.socialmediaapp.model.User;

public class UserMapper {
    public static UserPreview toUserPreview(User user) {
        if (user == null) return null;
        return new UserPreview(user.getId(), user.getTitle(), user.getFirstName(), user.getLastName(), user.getPicture());
    }

    public static UserFull toUserFull(User user) {
        LocationFull locationDto = null;
        if (user.getLocation() != null) {
            locationDto = new LocationFull(
                    user.getLocation().getStreet(),
                    user.getLocation().getCity(),
                    user.getLocation().getState(),
                    user.getLocation().getCountry(),
                    user.getLocation().getTimezone()
            );
        }

        return new UserFull(
                user.getId(),
                user.getTitle(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getDateOfBirth(),
                user.getRegisterDate(),
                user.getPhone(),
                user.getPicture(),
                locationDto
        );
    }
}
