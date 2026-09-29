package com.dinehub.user.service;

import com.dinehub.user.dto.UserResponse;
import com.dinehub.user.entity.User;

import java.util.List;


public interface UserService {

    //POST METHOD
    UserResponse createUser(User user) ;
    UserResponse login(String email,String password);

    //GET METHOD
    List<UserResponse> getUser(String name);
    UserResponse getUserByemail(String email);
    Boolean existsUserById(Long userId);

    //PUT METHOD
    UserResponse updateUser(String email, User user);

    //DELETE METHOD
    String deleteUser(String email);
}
