package ru.gb.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.store.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName (String name);
}
