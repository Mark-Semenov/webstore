package ru.gb.store.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/index")
    public String welcomePage(Model model) {
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }
}
