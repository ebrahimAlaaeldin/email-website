package com.cse.mail.dal.Adaptor;

import com.cse.mail.dto.UserDto;
import com.cse.mail.dal.model.User;
import com.cse.mail.dal.repository.UserRepository;
import com.cse.mail.dal.model.Contact; // Import other necessary classes

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

public class UserAdapter {

    @Autowired
    private UserRepository userRepository;

    public User dtoToEntity(UserDto dto) {
        if(dto.getUserId() == 0 ){
            return new User(dto.getUsername(), null);
        }
        else{
            User user = userRepository.findById(dto.getUserId()).orElse(null);
            // user.setUsername(dto.getUsername());
    
            return user;
        }
    }

    public static UserDto entityToDto(User user) {
        UserDto dto = new UserDto(user.getId(), user.getUsername());
      
        return dto;
    }
 
    // Add more methods if you need to handle List conversions or other special cases
}
