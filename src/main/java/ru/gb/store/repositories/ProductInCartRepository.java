package ru.gb.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.store.entities.ProductInCart;

public interface ProductInCartRepository extends JpaRepository<ProductInCart, Long> {


}
