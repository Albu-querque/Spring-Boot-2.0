package com.twitter.firstwitter.services;

import com.twitter.firstwitter.entities.Message;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MessageService {
    void deleteMessageById(long id);
    Message save(Message message, MultipartFile file);
    Iterable<Message> findAll();
    List<Message> findByTag(String tag);
}
