package com.diasonti.chatnoir.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatMessage {

    private Long id;

    private LocalDateTime sentAt;

    private long senderId;

    private String text;

}
