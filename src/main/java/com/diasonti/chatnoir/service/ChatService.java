package com.diasonti.chatnoir.service;

import com.diasonti.chatnoir.config.security.ChatUser;
import com.diasonti.chatnoir.entity.ChatMessage;
import com.diasonti.chatnoir.entity.ChatUserAccount;
import com.diasonti.chatnoir.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class ChatService {

    private final ChatMessageRepository messageRepository;

    @Value("${chat.message.limit:50}")
    private long messageLimit;

    public List<ChatMessage> getMessages() {
        return messageRepository.findAll();
    }

    public void postMessage(ChatUser sender, String text) {
        if(sender == null) {
            log.error("Method postMessage error: Sender should not be null");
            return;
        }
        ChatUserAccount senderAccount = sender.getUserAccount();
        ChatMessage message = new ChatMessage();
        message.setSentAt(LocalDateTime.now());
        message.setSenderId(senderAccount.getId());
        message.setText(text);
        messageRepository.save(message);
        trimMessages();
    }

    private void trimMessages() {
        long count = messageRepository.count();
        if(count > messageLimit)
            deleteOldestMessage();
    }

    private void deleteOldestMessage() {
        messageRepository.findOldest().ifPresent(messageRepository::remove);
    }

}
