package ru.gb.mark.webstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.mark.webstore.entity.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
        Brand findByTitle(String title);
}
