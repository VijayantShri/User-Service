package com.app.userservice.repositories;

import com.app.userservice.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepo extends JpaRepository<Token, Long> {

    Token findByValueAndDeletedEquals(String token, boolean isDeleted);
}
