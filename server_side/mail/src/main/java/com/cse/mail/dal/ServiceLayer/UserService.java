//package com.cse.mail.service;
//
//import com.cse.mail.adapter.UserAdapter;
//import com.cse.mail.dto.UserDto;
//import com.cse.mail.dal.model.User;
//import com.cse.mail.dal.repository.UserRepository;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    public UserDto createUser(UserDto userDto) {
//        User user = UserAdapter.dtoToEntity(userDto);
//        user = userRepository.save(user);
//        return UserAdapter.entityToDto(user);
//    }
//
//    // Other service methods
//}
