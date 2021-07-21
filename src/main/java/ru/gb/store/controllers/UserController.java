package ru.gb.store.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.store.dto.UserDTO;
import ru.gb.store.service.UserService;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder encoder;
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
        if (userDTO.getPassword().equals(userDTO.getMatchingPassword())) {
            userDTO.setPassword(encoder.encode(userDTO.getPassword()));
            userDTO.setRole("ROLE_USER");
            if (!userService.registerNewUserAccount(userDTO)) {
                emailExist = "This email has already exists";
                return "redirect:/reg?error=email-exist";
            }
        }
        return "redirect:/";
    }


}
