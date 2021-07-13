package ru.gb.store.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.store.entities.Category;
import ru.gb.store.entities.Product;

import java.math.BigDecimal;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByCategoryOrderByName(Pageable pageable, Category category);

    Page<Product> findAllByPriceBetween(BigDecimal price, BigDecimal price2, Pageable pageable);

}
