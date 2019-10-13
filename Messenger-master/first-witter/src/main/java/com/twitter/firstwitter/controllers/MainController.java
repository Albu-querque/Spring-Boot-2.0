package com.twitter.firstwitter.controllers;

import com.twitter.firstwitter.entities.Message;
import com.twitter.firstwitter.entities.User;
import com.twitter.firstwitter.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;


@Controller
public class MainController {
    @Autowired
    private MessageService messageService;

    @GetMapping("/")
    public String main(Model model) {
        return "main";
    }

    @GetMapping("/main")
    public String listMessages(@RequestParam(required = false, defaultValue = "") String tag, Model model) {
        Iterable<Message> messages;

        if (tag != null && !tag.isEmpty()) {
            messages = messageService.findByTag(tag);
        } else {
            messages = messageService.findAll();
        }

        model.addAttribute("tag", tag);
        model.addAttribute("messages", messages);

        return "listMessages";
    }



    @PostMapping("/main")
    public String save(@AuthenticationPrincipal User user,
                       @RequestParam("file") MultipartFile file,
                       @Valid Message message,
                       BindingResult bindingResult,
                       Model model) {
        message.setAuthor(user);
        messageService.save(message, file, bindingResult, model);

        Iterable<Message> messages = messageService.findAll();
        model.addAttribute("messages", messages);

        return "listMessages";
    }
}
