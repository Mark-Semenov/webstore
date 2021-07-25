package ru.gb.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.store.entities.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
        Brand findByTitle(String title);
}
