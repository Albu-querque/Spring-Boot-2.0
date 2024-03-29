package com.twitter.firstwitter.repositories;

import com.twitter.firstwitter.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    User findByActivationCode(String code);
}
