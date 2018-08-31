package com.diasonti.chatnoir.controller;

import com.diasonti.chatnoir.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(path = "/registration")
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class RegistrationController {

    private final RegistrationService registrationService;

    @RequestMapping(method = RequestMethod.POST)
    public String registration(@RequestParam String name,
                               @RequestParam String login,
                               @RequestParam String password,
                               RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("registerSuccess", registrationService.register(name, login, password));
        return "redirect:/login";
    }

}
