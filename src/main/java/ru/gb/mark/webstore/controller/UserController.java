package ru.gb.mark.webstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.mark.webstore.dto.UserDTO;
import ru.gb.mark.webstore.service.UserService;


@Controller
@RequestMapping
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private String emailExist = null;

    @GetMapping("/user")
    public String welcome() {
        return "user";

    }

    @GetMapping("/reg")
    public String registrationUser(Model model) {
        model.addAttribute("newUser", new UserDTO());
        model.addAttribute("emailExist", emailExist);
        return "reg";
    }

    @PostMapping("/reg")
    public String reg(UserDTO userDTO) {
        return userService.registerNewUserAccount(userDTO);
    }


}
