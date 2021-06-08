package ru.gb.store.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.store.entities.Role;
import ru.gb.store.entities.User;
import ru.gb.store.repositories.RoleRepository;
import ru.gb.store.service.UserService;

import java.util.Collections;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder encoder;

    @GetMapping
    public String welcome() {
        return "admin";
    }

    @GetMapping("/new_user")
    public String createUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("role", new Role());
        model.addAttribute("roles", roleRepository.findAll());
        return "create_user";
    }

    @PostMapping("/new_user")
    public String addNewUser(User user, Role role) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles(Collections.singletonList(roleRepository.findByName(role.getName())));
        userService.addUser(user);
        return "create_user";
    }


}
