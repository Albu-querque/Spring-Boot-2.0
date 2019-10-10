package com.twitter.firstwitter.services;

import com.twitter.firstwitter.entities.Role;
import com.twitter.firstwitter.entities.User;
import com.twitter.firstwitter.exceptions.NotFoundUser;
import com.twitter.firstwitter.repositories.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final MailSender mailSender;

    public UserServiceImpl(UserRepo userRepo, MailSender mailSender) {
        this.userRepo = userRepo;
        this.mailSender = mailSender;
    }

    @Override
    public boolean saveUser(User user) {
        User userFromDB = findByUsername(user.getUsername());

        if(userFromDB != null) {
            userFromDB = findByEmail(user.getEmail());
        }

        if(userFromDB != null) {
            return false;
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        userRepo.save(user);

        if(!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                "Hello, %s! \n" +
                        "Welcome to Twitter! \n" +
                        "Please, confirm your account, visit next link: http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );
            mailSender.send(user.getEmail(), "Confirm account", message);
        }
        return true;
    }

    @Override
    public boolean saveEditedUser(User user) {
        User userFromDB = findByUsername(user.getUsername());

        if(userFromDB == null) {
            userFromDB = findByEmail(user.getEmail());
        }

        if(userFromDB != null) {
            userRepo.save(user);
            return true;
        }


        return false;
    }

    @Override
    public boolean deleteUserById(long id) {
        userRepo.deleteById(id);
        return existsUser(id);
    }

    @Override
    public boolean existsUser(long id) {
        return userRepo.findById(id).isPresent();
    }

    @Override
    public User findById(long id) throws NotFoundUser {
        return userRepo.findById(id).orElseThrow(NotFoundUser::new);
    }

    @Override
    public List<User> getListUsers() {
        return userRepo.findAll();
    }

    @Override
    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public boolean activateUser(String code) {
        User user = userRepo.findByActivationCode(code);

        if(user == null) {
            return false;
        }
        user.setActivationCode(null);
        userRepo.save(user);
        return true;
    }
}
