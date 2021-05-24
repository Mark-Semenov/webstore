package ru.gb.store.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping
public class AdminController {

    @GetMapping("/admin")
    @ResponseBody
    public String welcome(Model model){
        return "hello admin";
    }



}
