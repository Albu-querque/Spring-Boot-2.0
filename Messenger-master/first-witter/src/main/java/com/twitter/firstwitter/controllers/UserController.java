package com.twitter.firstwitter.controllers;

import com.twitter.firstwitter.entities.Role;
import com.twitter.firstwitter.entities.User;
import com.twitter.firstwitter.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/userList")
    public String listUser(Model model) {
        model.addAttribute("users",userService.getListUsers());
        return "userList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{user}")
    public String editUserForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String saveEdit(@RequestParam String username,
                           @RequestParam Map<String, String> form,
                           @RequestParam("userID") User user) {
        userService.saveUser(username, user, form);
        return "redirect:/users/userList";
    }

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@AuthenticationPrincipal User user,
                                     @RequestParam String password,
                                     @RequestParam String email) {
        userService.updateProfile(user, password, email);
        return "redirect:/users/profile";
    }
}
