package com.app.userservice.controllers;

import com.app.userservice.models.Token;
import com.app.userservice.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    public Token login() {
        return null;
    }

    public User singUp() {
        return null;
    }

    public ResponseEntity<Void> logout() {
        return null;
    }
}
