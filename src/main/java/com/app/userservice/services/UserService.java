package com.app.userservice.services;

import com.app.userservice.models.Token;
import com.app.userservice.models.User;
import com.app.userservice.repositories.TokenRepo;
import com.app.userservice.repositories.UserRepo;
import lombok.NonNull;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private UserRepo userRepository;
    private TokenRepo tokenRepository;

    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder,
                       UserRepo userRepository,
                       TokenRepo tokenRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    public User signUp(String name, String email, String password) {
        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setHashedPassword(
                bCryptPasswordEncoder.encode(password)
        );
        User savedUser = userRepository.save(newUser);
        return savedUser;
    }

    public Token login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        System.out.println("True or not");
        if (userOptional.isEmpty()) {
            // throw exception
            return null;
        }
        User user = userOptional.get();
        System.out.println("User found: " + userOptional.get().getId());
        if (!bCryptPasswordEncoder.matches(password, user.getHashedPassword())) {
            return null;
        }

        // Token creation
        Token token = new Token();
        token.setUser(user);
        token.setValue(RandomStringUtils.randomAlphabetic(128));

        LocalDate today = LocalDate.now();
        LocalDate oneDayLater = today.plusDays(1);
        Date expiryAt = Date.from(oneDayLater.atStartOfDay(ZoneId.systemDefault()).toInstant());
        token.setExpiryAt(expiryAt);

        return tokenRepository.save(token);
    }

    public Void logout(String token) {
        Optional<Token> token1 = Optional.ofNullable(tokenRepository.findByValueAndDeletedEquals(token, false));
        if (token1.isEmpty()) {
//            throw exception
            return null;
        }

        Token token2 = token1.get();
        token2.setDeleted(true);
        tokenRepository.save(token2);
        return null;

    }

    public User validateToken(@NonNull String token) {
        Optional<Token> token1 = Optional.ofNullable(tokenRepository.findByValueAndDeletedEquals(token, false));

        if (token1.isEmpty()) {
            return null;
        }

        Token token2 = token1.get();
        if (token2.getExpiryAt().before(new Date()) || token2.isDeleted()) {
            return null;
        }

        return token2.getUser();
    }
}
