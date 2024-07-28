package com.app.productservice.security.services;

import com.app.productservice.security.dtos.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthenticationService {

//    private RestTemplate restTemplate;
//
//    public AuthenticationService(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//    public boolean authenticate(String token) {
//        String userValidateURL = "http://localhost:9000/user/validate";
//        ResponseEntity<User> userResponseEntity = restTemplate.postForEntity(
//                userValidateURL + token,
//                null,
//                User.class);
//
//        if (userResponseEntity.getBody() != null) {
//            return true;
//        }
//        return false;
//    }

}
