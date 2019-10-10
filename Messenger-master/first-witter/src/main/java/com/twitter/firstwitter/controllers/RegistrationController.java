package com.twitter.firstwitter.controllers;

import com.twitter.firstwitter.entities.User;
import com.twitter.firstwitter.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @Autowired
    UserService userService;

    @GetMapping("/registration")
    public String registrationPage(){
        return "/registration";
    }

    @PostMapping("/registration")
    public String registeredUser(User user, Model model) {

        if(!userService.saveUser(user)) {
            model.addAttribute("message", "User already exists!");
            return "/registration";
        }

        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(@PathVariable String code, Model model) {
        boolean isActivated = userService.activateUser(code);
        if(isActivated) {
            model.addAttribute("message", "You have successfully registered!");
        }
        else {
            model.addAttribute("message", "Activated code not found!");
        }

        return "login";
    }
}
