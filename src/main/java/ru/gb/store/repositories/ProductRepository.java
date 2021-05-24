package ru.gb.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.store.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
