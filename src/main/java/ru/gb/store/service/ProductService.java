package ru.gb.store.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.store.entities.AdminPanelBlock;
import ru.gb.store.entities.Category;
import ru.gb.store.entities.Product;
import ru.gb.store.repositories.AdminPanelBlockRepository;
import ru.gb.store.repositories.AdminURLRepository;
import ru.gb.store.repositories.BrandRepository;
import ru.gb.store.repositories.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Data
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private Page<Product> products;
    private Pageable pageable;
    private final AdminURLRepository adminURLRepository;
    private final AdminPanelBlockRepository adminPanelBlockRepository;
    private final BrandRepository brandRepository;

    public Page<Product> getPageWithProducts(Integer page, Category category, Map<String, String> filters) {
        pageable = PageRequest.of(page, 6, sortProd(filters));
        products = productRepository.findAll(pageable);

        if (category != null) {
            products = productRepository.findAllByCategoryOrderByName(pageable, category);
            if (searchProductsByBrand(filters.get("brandName"),products)) return products;
        }

        if (searchRequest(filters.get("search"))) return products;

        findByPriceBetween(filters);

        return products;
    }

    private Sort sortProd(Map<String, String> filters) {
        if (filters.get("minPrice") != null && filters.get("maxPrice") != null) {
            if (!filters.get("minPrice").isEmpty() && !filters.get("maxPrice").isEmpty()) {
                return Sort.by("price");
            }
        }

        return Sort.by("id");
    }

    private void findByPriceBetween(Map<String, String> filters) {
        Long min = !filters.get("minPrice").isEmpty() ? Long.valueOf(filters.get("minPrice")) : null;
        Long max = !filters.get("maxPrice").isEmpty() ? Long.valueOf(filters.get("maxPrice")) : null;
        if (min != null && max != null) {
            products = productRepository.findAllByPriceBetween(
                    BigDecimal.valueOf(min),
                    BigDecimal.valueOf(max),
                    pageable);
        }

    }

    public List<Product> findByName(String name) {
        return productRepository.findAllByNameContainingIgnoreCase(name);
    }

    public List<AdminPanelBlock> getAdminBlocks() {
        return adminPanelBlockRepository.findAll();
    }

    private boolean searchRequest(String productName) {
        if (!productName.isEmpty()) {
            products = new PageImpl<>(productRepository.findAll().stream().filter(product -> product.getName()
                    .toLowerCase().matches(".*" + productName.toLowerCase() + ".*"))
                    .collect(Collectors.toList()));
        }
        return products.isEmpty();
    }

    private boolean searchProductsByBrand(String brandName, Page<Product> products) {
        if (brandName != null && !brandName.isEmpty()) {
            this.products = new PageImpl<>(products.stream()
                    .filter(product -> product.getBrand().getTitle().equals(brandName))
                    .collect(Collectors.toList()));
        }
        return this.products.isEmpty();
    }

    public Product findProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Transactional
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @Transactional
    public void removeProduct(Long id) {
        productRepository.deleteById(id);
    }

}
