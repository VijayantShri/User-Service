package com.app.userservice.controllers;

import com.app.userservice.dtos.SignUpRequestDto;
import com.app.userservice.dtos.SignUpResponseDto;
import com.app.userservice.models.Token;
import com.app.userservice.models.User;
import com.app.userservice.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public Token login() {
        return null;
    }

    @PostMapping("/signup")
    public SignUpResponseDto singUp(@RequestBody SignUpRequestDto requestDto) {
        return toSignUpResponseDto(userService.signUp(requestDto.getName(),
                requestDto.getEmail(), requestDto.getPassword()
        ));
    }

    public ResponseEntity<Void> logout() {
        return null;
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
}
