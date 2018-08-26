package com.sonti.chatnoir.repository;

import com.sonti.chatnoir.entity.ChatUserAccount;

public interface UserRepository {

    ChatUserAccount findByEmail(String email);

    ChatUserAccount save(ChatUserAccount chatUserAccount);

}
