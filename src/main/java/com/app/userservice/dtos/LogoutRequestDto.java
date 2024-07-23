package com.app.userservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogoutRequestDto {
    private String token;
}
