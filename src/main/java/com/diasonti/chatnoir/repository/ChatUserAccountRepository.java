package com.diasonti.chatnoir.repository;

import com.diasonti.chatnoir.entity.ChatUserAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatUserAccountRepository extends CrudRepository<ChatUserAccount, Long> {

    ChatUserAccount findByLogin(String login);

}
