package com.twitter.firstwitter.services;

import com.twitter.firstwitter.entities.User;
import com.twitter.firstwitter.exceptions.NotFoundUser;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface UserService extends UserDetailsService {
    boolean saveUser(User user);
    boolean saveEditedUser(User user);
    boolean deleteUserById(long id);
    boolean existsUser(long id);
    User findById(long id) throws NotFoundUser;
    List<User> getListUsers();
    User findByUsername(String username);
    User findByEmail(String email);


    boolean activateUser(String code);
}
