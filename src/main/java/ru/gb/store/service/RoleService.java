package ru.gb.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.gb.store.entities.Role;
import ru.gb.store.repositories.RoleRepository;

import java.util.List;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public List<String> getNamesOfRoles() {
        return roleRepository.findAll().stream().map(Role::getName).collect(Collectors.toList());
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
