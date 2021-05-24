package ru.gb.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.store.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
