package com.diasonti.chatnoir.controller;

import com.diasonti.chatnoir.config.security.ChatUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RootController {

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(@AuthenticationPrincipal ChatUser user) {
        if (user == null)
            return "redirect:/login";
        else
            return "redirect:/chat";
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String login(@AuthenticationPrincipal ChatUser user,
                        @RequestParam(defaultValue = "false") boolean error,
                        @RequestParam(defaultValue = "false") boolean logout,
                        Model model) {
        if (user != null)
            return "redirect:/chat";
        model.addAttribute("error", error);
        model.addAttribute("logout", logout);
        return "login";
    }

    @RequestMapping(path = "/chat", method = RequestMethod.GET)
    public String chat(@AuthenticationPrincipal ChatUser user) {
        if (user == null)
            return "redirect:/login";
        else
            return "chat";
    }

    @RequestMapping(path = "/user", method = RequestMethod.GET)
    @ResponseBody
    public ChatUser user(@AuthenticationPrincipal ChatUser user) {
        return user;
    }

}
