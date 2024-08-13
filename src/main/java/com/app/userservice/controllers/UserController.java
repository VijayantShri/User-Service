package com.app.userservice.controllers;

import com.app.userservice.dtos.LoginRequestDto;
import com.app.userservice.dtos.LogoutRequestDto;
import com.app.userservice.dtos.SignUpRequestDto;
import com.app.userservice.dtos.SignUpResponseDto;
import com.app.userservice.models.Token;
import com.app.userservice.models.User;
import com.app.userservice.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public Token login(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            return userService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/signup")
    public SignUpResponseDto singUp(@RequestBody SignUpRequestDto requestDto) {
        return toSignUpResponseDto(userService.signUp(requestDto.getName(),
                requestDto.getEmail(), requestDto.getPassword()
        ));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto logoutRequestDto) {
        userService.logout(logoutRequestDto.getToken());
        return ResponseEntity.ok().build();
    }

    public SignUpResponseDto toSignUpResponseDto(User user) {
        if (user == null) {
            return null;
        }
        SignUpResponseDto responseDto = new SignUpResponseDto();
        responseDto.setName(user.getName());
        responseDto.setEmail(user.getEmail());
        responseDto.setEmailVerified(user.isEmailVerified());

        return responseDto;
    }

    @PostMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        System.out.println("I have been called");
        return new User();
    }
}
