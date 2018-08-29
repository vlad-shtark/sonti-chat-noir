package com.diasonti.chatnoir.entity;

import lombok.Data;

@Data
public class ChatUserAccount {

    private Long id;

    private String login;

    private String password;

    private String name;

    private String role;

}
