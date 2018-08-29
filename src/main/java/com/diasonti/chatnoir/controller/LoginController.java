package com.diasonti.chatnoir.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
public class LoginController {

    private static final String LOGIN_PAGE = "login";

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return LOGIN_PAGE;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public Principal loginPage(Principal chatUser) {
        return chatUser;
    }

}