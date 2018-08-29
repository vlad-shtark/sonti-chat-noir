package com.diasonti.chatnoir.controller;

import com.diasonti.chatnoir.config.security.ChatUser;
import com.diasonti.chatnoir.entity.RestResponse;
import com.diasonti.chatnoir.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasAnyRole('CHAT_USER', 'ADMIN')")
@RequestMapping(path = "/chat")
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class RestChatController {

    private final ChatService chatService;

    @RequestMapping(path = "/get", method = RequestMethod.GET)
    public RestResponse getMessages(ChatUser user) {
        return RestResponse.ok(chatService.getMessages());
    }

    @RequestMapping(path = "/post", method = RequestMethod.POST)
    public RestResponse postMessage(ChatUser user, String message) {
        chatService.postMessage(user, message);
        return RestResponse.ok();
    }

}
