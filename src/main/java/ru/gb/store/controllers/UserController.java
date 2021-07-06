package ru.gb.store.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.store.dto.UserDTO;
import ru.gb.store.repositories.RoleRepository;
import ru.gb.store.service.UserService;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder encoder;

    @GetMapping("/user")
    public String welcome(Model model) {
        return "user";

    }

    @GetMapping("/reg")
    public String registrationUser(Model model){
        model.addAttribute("newUser", new UserDTO());
        return "reg";
    }

    @PostMapping("/reg")
    public String reg(UserDTO userDTO){
        if (userDTO.getPassword().equals(userDTO.getMatchingPassword())){
            userDTO.setPassword(encoder.encode(userDTO.getPassword()));
            userDTO.setRole("ROLE_USER");
            userService.addUser(userDTO);
        }
        return "cart";
    }




}
