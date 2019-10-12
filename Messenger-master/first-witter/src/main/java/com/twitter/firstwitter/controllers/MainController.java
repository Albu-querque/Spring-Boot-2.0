package com.twitter.firstwitter.controllers;

import com.twitter.firstwitter.entities.Message;
import com.twitter.firstwitter.entities.User;
import com.twitter.firstwitter.repositories.MessageRepo;
import com.twitter.firstwitter.services.MessageService;
import com.twitter.firstwitter.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class MainController {
    @Autowired
    private MessageService messageService;

    @GetMapping("/")
    public String main(Model model) {
        return "main";
    }

    @GetMapping("/main")
    public String listMessage(@RequestParam(required = false, defaultValue = "") String tag, Model model) {
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



    @PostMapping("/saveMessage")
    public String save(@AuthenticationPrincipal User user,
                       @RequestParam String text,
                       @RequestParam String tag,
                       @RequestParam("file") MultipartFile file,
                       Model model) throws IOException {
        messageService.save(new Message(text, tag, user), file);
        return "redirect:/main";
    }
}
