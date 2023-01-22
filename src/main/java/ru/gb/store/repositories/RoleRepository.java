package ru.gb.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.store.entities.Role;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    List<Role> findByName(String name);
}
