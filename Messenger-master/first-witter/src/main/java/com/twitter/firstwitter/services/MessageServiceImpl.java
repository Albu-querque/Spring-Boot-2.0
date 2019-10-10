package com.twitter.firstwitter.services;

import com.twitter.firstwitter.entities.Message;
import com.twitter.firstwitter.repositories.MessageRepo;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MessageServiceImpl implements MessageService{

    private final MessageRepo messageRepo;

    public MessageServiceImpl(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    @Override
    public void deleteMessageById(long id) {
        messageRepo.deleteById(id);
    }

    @Override
    public Message save(Message message) {
        return messageRepo.save(message);
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
