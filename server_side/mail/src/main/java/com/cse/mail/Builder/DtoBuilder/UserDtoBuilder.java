package com.cse.mail.Builder.DtoBuilder;

import com.cse.mail.dto.UserDto;

public class UserDtoBuilder {
    private String username;
    private int userId;

    public UserDtoBuilder setUsername(String username) {
        this.username = username;
        return this;
    }
    
    public UserDto build() {

        return new UserDto(userId, username);
    }

}
