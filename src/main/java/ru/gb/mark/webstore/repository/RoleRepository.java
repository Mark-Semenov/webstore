package ru.gb.mark.webstore.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.mark.webstore.entity.Role;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    List<Role> findByName(String name);
}
