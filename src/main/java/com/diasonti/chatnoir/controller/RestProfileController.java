package com.diasonti.chatnoir.controller;

import com.diasonti.chatnoir.config.security.ChatUser;
import com.diasonti.chatnoir.entity.RestResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/profile")
public class RestProfileController {

    @RequestMapping(path = "/get", method = RequestMethod.GET)
    public RestResponse getMessages(ChatUser user) {
        return RestResponse.ok();
    }

    @RequestMapping(path = "/get/{userId}", method = RequestMethod.GET)
    public RestResponse getMessages(ChatUser user, @PathVariable long userId) {
        return RestResponse.ok();
    }

    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public RestResponse postMessage(ChatUser user) {
        return RestResponse.ok();
    }

}
