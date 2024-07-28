package com.app.productservice.security.dtos;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Setter
@Getter
public class User {
    private String name;
    private String email;
    private String hashedPassword;
    private List<Role> roles;
    private boolean isEmailVerified;
}
