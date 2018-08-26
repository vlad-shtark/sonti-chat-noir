package com.sonti.chatnoir.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "chat_user")
public class ChatUserAccount {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String login;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private String role;

}
