package ru.gb.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.store.entities.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
