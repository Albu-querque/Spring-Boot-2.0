package com.twitter.firstwitter.services;

import com.twitter.firstwitter.entities.Role;
import com.twitter.firstwitter.entities.User;
import com.twitter.firstwitter.exceptions.NotFoundUser;
import com.twitter.firstwitter.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

import static java.util.stream.Collectors.toSet;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final UserRepo userRepo;
    private final MailSender mailSender;

    public UserServiceImpl(UserRepo userRepo, MailSender mailSender) {
        this.userRepo = userRepo;
        this.mailSender = mailSender;
    }

    @Override
    public boolean addUser(User user) {
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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        sendMessage(user);

        return true;
    }

    private void sendMessage(User user) {
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
    }

    @Override
    public void saveUser(String username, User user, Map<String, String> form) {
        user.setUsername(username);
        user.getRoles().clear();
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(toSet());

        for (String key : form.keySet()) {
            if(roles.contains(key)){
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepo.save(user);
    }

    @Override
    public void updateProfile(User user, String password, String email) {
        String userEmail = user.getEmail();
        boolean isEmailChanged = ((email != null && !email.equals(userEmail)) || (userEmail != null && !userEmail.equals(email)));

        if(isEmailChanged) {
            user.setEmail(email);

            if(!StringUtils.isEmpty(email)) {
                user.setActivationCode(UUID.randomUUID().toString());
            }
        }

        if(!StringUtils.isEmpty(password)) {
            user.setPassword(password);
        }

        userRepo.save(user);

        if(isEmailChanged) {
            sendMessage(user);
        }

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
