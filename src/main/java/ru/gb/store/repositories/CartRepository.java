package ru.gb.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.store.entities.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

}
