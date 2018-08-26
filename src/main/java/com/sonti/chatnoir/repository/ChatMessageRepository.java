package com.sonti.chatnoir.repository;

import com.sonti.chatnoir.entity.ChatMessage;
import org.springframework.data.repository.CrudRepository;

public interface ChatMessageRepository extends CrudRepository<ChatMessage, Long> {

}
