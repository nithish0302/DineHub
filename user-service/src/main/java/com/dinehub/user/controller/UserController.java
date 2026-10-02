package com.dinehub.user.controller;

import com.dinehub.user.dto.LoginResponse;
import com.dinehub.user.dto.UpdateUserRequest;
import com.dinehub.user.dto.UserResponse;
import com.dinehub.user.entity.User;
import com.dinehub.user.service.RefreshTokenService;
import com.dinehub.user.service.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;
    private final RefreshTokenService refreshTokenService;

    //POST METHOD
    @PostMapping("/createUser")
    public ResponseEntity<UserResponse>createuser(@Valid @RequestBody User user){
        UserResponse response=userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse>login(@NotBlank(message = "Email is required")@Email(message = "Invaild Email Format") @RequestParam String email, @NotBlank(message = "Password is required") @RequestParam String password  ){
        LoginResponse user=userService.login(email,password);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refreshAccessToken(
            @RequestParam String refreshToken) {

        String accessToken =
                refreshTokenService.refreshAccessToken(refreshToken);

        return ResponseEntity.ok(accessToken);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            @RequestParam String refreshToken) {

        refreshTokenService.deleteRefreshToken(refreshToken);

        return ResponseEntity.ok("Logout successful");
    }

    //GET METHOD
    @GetMapping("/getUsers")
    public ResponseEntity<List<UserResponse>> getUsers(@NotBlank(message = "Name is required") @RequestParam String name){



        List<UserResponse>users=userService.getUser(name);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/getUserByEmail/{email}")
    public ResponseEntity<UserResponse>getUserByEmail(@NotBlank(message = "Email is required") @Email(message = "Invalid Format of Email") @PathVariable String email )
    {


        UserResponse user=userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/exists/{userId}")
    public ResponseEntity<Boolean>existUserById(@NotNull(message = "User Id needed") @PathVariable Long userId){
        return ResponseEntity.ok(userService.existsUserById(userId));
    }

    //PUT METHOD
    @PutMapping("/updateUser")
    public ResponseEntity<UserResponse> updateUser(
            @NotBlank(message = "Email is required")
            @Email(message = "Invalid Email Format")
            @RequestParam String email,

            @Valid @RequestBody UpdateUserRequest request) {

        UserResponse response =
                userService.updateUser(email, request);

        return ResponseEntity.ok(response);
    }

    //DELETE METHOD
    @DeleteMapping("/deleteUser/{email}")
    public ResponseEntity<String>deleteUser(@NotBlank(message = "Email is required") @Email(message = "Invalid Format of Email") @PathVariable String email ){


        String response=userService.deleteUser(email);
        return ResponseEntity.ok(response);
    }
}
