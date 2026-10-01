package com.dinehub.user.service;

import com.dinehub.user.dto.LoginResponse;
import com.dinehub.user.dto.UserResponse;
import com.dinehub.user.entity.User;

import java.util.List;

public interface UserService {

    // POST METHOD
    UserResponse createUser(User user);

    LoginResponse login(String userEmail, String password);

    // GET METHOD
    List<UserResponse> getUser(String userName);

    UserResponse getUserByEmail(String userEmail);

    Boolean existsUserById(Long userId);

    // PUT METHOD
    UserResponse updateUser(String userEmail, User user);

    // DELETE METHOD
    String deleteUser(String userEmail);
}