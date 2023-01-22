package ru.gb.mark.webstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.gb.mark.webstore.entity.Brand;
import ru.gb.mark.webstore.entity.Category;
import ru.gb.mark.webstore.entity.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

    Optional<Product> findById(Long id);

    List<Product> findAll();

    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Product> findAllByCategoryOrderByName(Pageable pageable, Category category);

    Page<Product> findAllByPriceBetween(BigDecimal price, BigDecimal price2, Pageable pageable);

    List<Product> findByBrand(String name);

    List<Product> findAllByNameContainingIgnoreCase(String name);

    void save(Product product);

    void deleteById(Long id);

}
