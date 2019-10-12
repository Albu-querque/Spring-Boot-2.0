package com.twitter.firstwitter.services;

import com.twitter.firstwitter.entities.Message;
import com.twitter.firstwitter.repositories.MessageRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
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
    public Message save(Message message, MultipartFile file)  {

        if(file != null && !file.getOriginalFilename().isEmpty()) {
            File dir = new File(uploadPath);
            if(!dir.exists()){
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
