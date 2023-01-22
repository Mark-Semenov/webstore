package ru.gb.mark.webstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.gb.mark.webstore.entity.Role;
import ru.gb.mark.webstore.repository.RoleRepository;

import java.util.List;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public List<String> getNamesOfRoles() {
        return roleRepository.findAll().stream().map(Role::getName).collect(Collectors.toList());
    }
}
