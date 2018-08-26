package com.sonti.chatnoir.repository;

import com.sonti.chatnoir.entity.ChatUserAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatUserAccountRepository extends CrudRepository<ChatUserAccount, Long> {

    ChatUserAccount findByLogin(String login);

}
