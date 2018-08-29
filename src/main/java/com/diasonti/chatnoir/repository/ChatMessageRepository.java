package com.diasonti.chatnoir.repository;

import com.diasonti.chatnoir.entity.ChatMessage;
import org.springframework.data.repository.CrudRepository;

public interface ChatMessageRepository extends CrudRepository<ChatMessage, Long> {

}
