package com.twitter.firstwitter.services;

import com.twitter.firstwitter.controllers.ControllerUtils;
import com.twitter.firstwitter.entities.Message;
import com.twitter.firstwitter.repositories.MessageRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.util.List;

import java.util.Map;
import java.util.UUID;

@Service
public class MessageServiceImpl implements MessageService{

    private final MessageRepo messageRepo;

    @Value("${upload.path}")
    private String uploadPath;

    public MessageServiceImpl(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    @Override
    public void deleteMessageById(long id) {
        messageRepo.deleteById(id);
    }

    @Override
    public Message save(Message message, MultipartFile file, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes)  {

        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("message", message);
            ControllerUtils.getErrors(bindingResult).forEach(redirectAttributes::addFlashAttribute);
        } else {
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                File dir = new File(uploadPath);
                if (!dir.exists()) {
                    dir.mkdir();
                }

                String uuidFileName = UUID.randomUUID().toString();
                String resultFileName = uuidFileName + "." + file.getOriginalFilename();
                try {
                    file.transferTo(new File(uploadPath + "/" + resultFileName));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                message.setFilename(resultFileName);
            }

            model.addAttribute("message", null);
            return messageRepo.save(message);
        }

        return null;
    }

    @Override
    public Iterable<Message> findAll() {
        return messageRepo.findAll();
    }

    @Override
    public List<Message> findByTag(String tag) {
        return messageRepo.findByTag(tag);
    }
}
