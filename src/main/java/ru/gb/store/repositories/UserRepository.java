package ru.gb.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.store.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByLogin(String login);

}
