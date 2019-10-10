package com.twitter.firstwitter.services;

import com.twitter.firstwitter.entities.Message;

import java.util.List;

public interface MessageService {
    void deleteMessageById(long id);
    Message save(Message message);
    Iterable<Message> findAll();
    List<Message> findByTag(String tag);
}
