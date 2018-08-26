package com.sonti.chatnoir.repository;

import com.sonti.chatnoir.entity.ChatUserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
@Profile("dev")
public class ListUserRepository implements UserRepository {

    private static final List<ChatUserAccount> users = new ArrayList<>();

    @PostConstruct
    private void init() {
        ChatUserAccount admin = new ChatUserAccount();
        admin.setEmail("admin");
        admin.setPassword(new BCryptPasswordEncoder().encode("admin"));
        admin.setRoles(Collections.singletonList("ADMIN"));
        users.add(admin);
    }

    @Override
    public ChatUserAccount findByEmail(String email) {
        return users.stream().filter(user -> user.getEmail().equals(email)).findFirst().orElse(null);
    }

    @Override
    public ChatUserAccount save(ChatUserAccount chatUserAccount) {
        if(users.stream().anyMatch(user -> user.getEmail().equals(chatUserAccount.getEmail())))
            return null;
        users.add(chatUserAccount);
        return chatUserAccount;
    }
}
