package ru.gb.mark.webstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.mark.webstore.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
