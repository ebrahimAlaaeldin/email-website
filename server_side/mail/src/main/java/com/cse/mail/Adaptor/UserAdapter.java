package com.cse.mail.Adaptor;

import com.cse.mail.dto.UserDto;
import com.cse.mail.Builder.DataBaseBuilder.UserBuilder;
import com.cse.mail.Builder.DtoBuilder.UserDtoBuilder;
import com.cse.mail.dal.model.User;
import com.cse.mail.dal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserAdapter {

    @Autowired
    private UserRepository userRepository;

    public User dtoToEntity(UserDto dto) {
        if(dto.getUserId() == 0 ){
            User user = new UserBuilder().setUsername(dto.getUsername()).build();
            return user;
        }
        else{
            User user = userRepository.findById(dto.getUserId()).orElse(null);
            // user.setUsername(dto.getUsername());
    
            return user;
        }
    }

    public static UserDto entityToDto(User user) {
        
        UserDto dto = new UserDtoBuilder().setUsername(user.getUsername()).build();
      
        return dto;
    }
 
    // Add more methods if you need to handle List conversions or other special cases
}
