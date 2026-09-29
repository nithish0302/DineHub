package com.dinehub.user.controller;

import com.dinehub.user.dto.UserResponse;
import com.dinehub.user.entity.User;
import com.dinehub.user.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    //POST METHOD
    @PostMapping("/createUser")
    public ResponseEntity<UserResponse>createuser(@Valid @RequestBody User user){
        UserResponse response=userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse>login(@NotBlank(message = "Email is required")@Email(message = "Invaild Email Format") @RequestParam String email, @NotBlank(message = "Password is required") @RequestParam String password, HttpSession session){
        UserResponse user=userService.login(email,password);
        session.setAttribute("user",user.getUserEmail());
        return ResponseEntity.ok(user);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {

        session.invalidate();

        return ResponseEntity.ok("Logout successful");
    }

    //GET METHOD
    @GetMapping("/getUsers")
    public ResponseEntity<List<UserResponse>> getUsers(@NotBlank(message = "Name is required") @RequestParam String name,HttpSession session){

        if(session.getAttribute("user")==null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<UserResponse>users=userService.getUser(name);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/getUserByEmail/{email}")
    public ResponseEntity<UserResponse>getUserByEmail(@NotBlank(message = "Email is required") @Email(message = "Invalid Format of Email") @PathVariable String email,HttpSession session)
    {
        if(session.getAttribute("user")==null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserResponse user=userService.getUserByemail(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/exists/{userId}")
    public ResponseEntity<Boolean>existUserById(@NotNull(message = "User Id needed") @PathVariable Long userId){
        return ResponseEntity.ok(userService.existsUserById(userId));
    }

    //PUT METHOD
    @PutMapping("/updateUser")
    public ResponseEntity<UserResponse>updateUser(@NotBlank(message = "Email is required") @Email(message = "Invalid Format of Email") @RequestParam String email,@Valid @RequestBody User user,HttpSession session)
    {
        if(session.getAttribute("user")==null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserResponse response=userService.updateUser(email,user);
        return ResponseEntity.ok(response);
    }

    //DELETE METHOD
    @DeleteMapping("/deleteUser/{email}")
    public ResponseEntity<String>deleteUser(@NotBlank(message = "Email is required") @Email(message = "Invalid Format of Email") @PathVariable String email,HttpSession session){
        if(session.getAttribute("user")==null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String response=userService.deleteUser(email);
        return ResponseEntity.ok(response);
    }
}
