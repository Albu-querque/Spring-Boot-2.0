package com.twitter.firstwitter.controllers;

import com.twitter.firstwitter.entities.User;
import com.twitter.firstwitter.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    @Autowired
    UserService userService;

    @GetMapping("/registration")
    public String registrationPage(){
        return "/registration";
    }

    @PostMapping("/registration")
    public String registeredUser(@RequestParam("passwordConfirmation") String passwordConfirmation,
                                 @Valid User user,
                                 BindingResult bindingResult,
                                 Model model) {
        boolean isConfirmationPasswordEmpty = StringUtils.isEmpty(passwordConfirmation);
        if(isConfirmationPasswordEmpty) {
            model.addAttribute("passwordConfirmationError", "Need confirm password");
        }
        if(user.getPassword() != null && !user.getPassword().equals(passwordConfirmation)) {
            model.addAttribute("passwordError", "Passwords do not match!");
        }
        if(isConfirmationPasswordEmpty || bindingResult.hasErrors()) {
            model.mergeAttributes(ControllerUtils.getErrors(bindingResult));
            return "registration";
        }
        if(!userService.addUser(user)) {
            model.addAttribute("usernameError", "User already exists!");
            return "registration";
        }

        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(@PathVariable String code, Model model) {
        boolean isActivated = userService.activateUser(code);
        if(isActivated) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "You have successfully registered!");
        }
        else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Activated code not found!");
        }

        return "login";
    }
}
