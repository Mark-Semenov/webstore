package ru.gb.mark.webstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.mark.webstore.entity.ProductInCart;

public interface ProductInCartRepository extends JpaRepository<ProductInCart, Long> {


}
