package com.twitter.firstwitter.services;

import com.twitter.firstwitter.entities.Message;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

public interface MessageService {
    void deleteMessageById(long id);
    Message save(Message message, MultipartFile file, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes);
    Iterable<Message> findAll();
    List<Message> findByTag(String tag);
}
