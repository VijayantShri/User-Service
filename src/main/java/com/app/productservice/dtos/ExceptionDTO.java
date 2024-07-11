package com.app.productservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExceptionDTO {
    private String message;
    private String status;
}
