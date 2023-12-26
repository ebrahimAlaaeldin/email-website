package com.cse.mail.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.cse.mail.dal.model.User} entity
 */
public class UserDto implements Serializable {
    private final int userId;
    private final String username;

    public UserDto(int id, String username) {
        this.userId = id;
        this.username = username;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto entity = (UserDto) o;
        return Objects.equals(this.userId, entity.userId) &&
                Objects.equals(this.username, entity.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + userId + ", " +
                "username = " + username + ")";
    }
}