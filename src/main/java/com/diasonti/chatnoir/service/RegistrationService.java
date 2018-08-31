package com.diasonti.chatnoir.service;

import com.diasonti.chatnoir.entity.ChatUserAccount;
import com.diasonti.chatnoir.repository.ChatUserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class RegistrationService {

    private final static String CHAT_USER_ROLE = "CHAT_USER";

    private final PasswordEncoder passwordEncoder;

    private final ChatUserAccountRepository userAccountRepository;

    public boolean register(String name, String login, String password) {
        if (userAccountRepository.exists(login))
            return false;
        ChatUserAccount reg = new ChatUserAccount();
        reg.setName(name);
        reg.setLogin(login);
        reg.setPassword(passwordEncoder.encode(password));
        reg.setRole(CHAT_USER_ROLE);
        userAccountRepository.save(reg);
        return true;
    }

}
