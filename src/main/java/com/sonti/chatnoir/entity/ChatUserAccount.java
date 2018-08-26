package com.sonti.chatnoir.entity;

import lombok.Data;

import java.util.List;

@Data
public class ChatUserAccount {

    private String email;
    private String password;
    private List<String> roles;

}
