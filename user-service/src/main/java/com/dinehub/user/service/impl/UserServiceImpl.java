package com.dinehub.user.service.impl;

import com.dinehub.user.dto.UserResponse;
import com.dinehub.user.entity.User;
import com.dinehub.user.exception.UserAlreadyExistsException;
import com.dinehub.user.exception.UserNotFoundException;
import com.dinehub.user.repository.UserRepository;
import com.dinehub.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //POST METHOD
    @Override
    public UserResponse createUser(User user) {

        if(userRepository.existsByUserEmail(user.getUserEmail()))
        {
            throw new UserAlreadyExistsException("User Already Exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User newUser = userRepository.save(user);

        return convertToUserResponse(newUser);
    }

    @Override
    public UserResponse login(String userEmail, String password)
    {
        User user = userRepository.findByUserEmail(userEmail);
        if(user == null)
        {
            throw new UserNotFoundException("User Not Found ,Please Create the new account");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UserNotFoundException("Invalid email or password");
        }
        return convertToUserResponse(user);
    }

    //GET METHOD
    @Override
    public List<UserResponse> getUser(String userName) {
        List<User> userList = userRepository.findByUserName(userName);

        List<UserResponse> response = new ArrayList<>();
        for(User user : userList)
        {
            response.add(convertToUserResponse(user));
        }
        return response;
    }

    @Override
    public UserResponse getUserByEmail(String userEmail) {

        User user = userRepository.findByUserEmail(userEmail);

        if (user == null) {
            throw new UserNotFoundException("User Not Found");
        }

        return convertToUserResponse(user);
    }

    @Override
    public Boolean existsUserById(Long userId)
    {
        Boolean response=userRepository.existsById(userId);
        return response;
    }

    //PUT METHOD
    @Override
    public UserResponse updateUser(String userEmail, User user) {
        User existingUser = userRepository.findByUserEmail(userEmail);

        if (existingUser == null) {
            throw new UserNotFoundException("User Not Found");
        }

        existingUser.setUserName(user.getUserName());
        existingUser.setUserPhoneNumber(user.getUserPhoneNumber());
        existingUser.setUserAddress(user.getUserAddress());
        existingUser.setUserRole(user.getUserRole());

        User updatedUser = userRepository.save(existingUser);

        return convertToUserResponse(updatedUser);
    }

    //DELETE METHOD
    @Override
    @Transactional
    public String deleteUser(String userEmail) {

        if (!userRepository.existsByUserEmail(userEmail)) {
            throw new UserNotFoundException("User Not Found");
        }

        userRepository.deleteByUserEmail(userEmail);

        return "User deleted successfully";
    }

    private UserResponse convertToUserResponse(User user){
        return new UserResponse(
                user.getUserId(),
                user.getUserName(),
                user.getUserEmail(),
                user.getUserPhoneNumber(),
                user.getUserAddress(),
                user.getUserRole()
        );
    }
}