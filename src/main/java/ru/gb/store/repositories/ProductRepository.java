package ru.gb.store.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.store.entities.Category;
import ru.gb.store.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByCategory(Pageable pageable, Category category);

}
