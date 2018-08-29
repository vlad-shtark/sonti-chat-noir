package com.diasonti.chatnoir.service;

import com.diasonti.chatnoir.config.ChatNoirApplication;
import com.diasonti.chatnoir.config.security.ChatUser;
import com.diasonti.chatnoir.entity.ChatMessage;
import com.diasonti.chatnoir.entity.ChatUserAccount;
import com.diasonti.chatnoir.repository.ChatMessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ChatNoirApplication.class)
@Slf4j
public class ChatServiceTest {

    @Autowired
    private ChatService chatService;

    @MockBean
    private ChatMessageRepository messageRepository;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getMessages() {
        when(messageRepository.findAll())
                .thenReturn(null)
                .thenReturn(Collections.emptyList())
                .thenReturn(Arrays.asList(new ChatMessage(), new ChatMessage(), new ChatMessage()));
        assertNull(chatService.getMessages());
        assertTrue(chatService.getMessages().isEmpty());
        assertEquals(chatService.getMessages().size(), 3);
    }

    @Test
    public void postMessage() {
        final long limit = 30;
        LinkedList<ChatMessage> table = new LinkedList<>();

        doAnswer(invocation -> {
            log.debug("Mocking");
            long nextId = 1;
            ChatMessage maxId = table.stream().max(Comparator.comparingLong(ChatMessage::getId)).orElse(null);
            if(maxId != null)
                nextId = maxId.getId() + 1L;
            log.debug("Mocking nextId: {}", nextId);
            ChatMessage newMessage = invocation.getArgument(0);
            log.debug("Mocking newMessage: {}", newMessage);
            newMessage.setId(nextId);
            table.addLast(newMessage);
            if(table.size() > limit)
                table.removeFirst();
            log.debug("Mocking table.first: {}", table.getFirst());
            log.debug("Mocking table.last: {}", table.getLast());
            return null;
        }).when(messageRepository).save(any(ChatMessage.class));

        Mockito.doReturn(table).when(messageRepository).findAll();

        final ChatUser user = new ChatUser("username", "password", Collections.singletonList(new SimpleGrantedAuthority("CHAT_USER")));
        final ChatUserAccount userAccount = new ChatUserAccount();
        userAccount.setId(1L);
        userAccount.setName("name");
        userAccount.setLogin("login");
        userAccount.setPassword("password");
        userAccount.setRole("CHAT_USER");
        user.setUserAccount(userAccount);
        for(int i = 0; i < 100; i++) {
            log.debug("Adding message {}", i);
            chatService.postMessage(user, String.valueOf(i));
            assertTrue(table.size() <= limit);
            assertEquals(table.getLast().getText(), String.valueOf(i));
            if(i >= limit) {
                assertEquals(table.getFirst().getText(), String.valueOf(i - limit + 1));
                assertEquals(table.size(), limit);
            }
        }

    }
}
