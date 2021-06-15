package ru.gb.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.store.entities.AdminPanelBlock;
import ru.gb.store.entities.AdminURL;
import ru.gb.store.entities.Category;
import ru.gb.store.entities.Product;
import ru.gb.store.repositories.AdminPanelBlockRepository;
import ru.gb.store.repositories.AdminURLRepository;
import ru.gb.store.repositories.ProductRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private List<Integer> pages;
    private Page<Product> products;
    private Pageable pageable;
    private final AdminURLRepository adminURLRepository;
    private final AdminPanelBlockRepository adminPanelBlockRepository;

    public Page<Product> getPageWithProducts(Integer page, Category category, String... filter) {
        pages = new ArrayList<>();
        pageable = PageRequest.of(page, 5, Sort.by("id"));
        products = productRepository.findAll(pageable);

        if (category != null) {
            products = productRepository.findAllByCategory(pageable, category);
        }

        if (filter != null && filter[0] != null && filter[1] != null) {
            products = productRepository.findAllByPriceBetween(
                    BigDecimal.valueOf(Long.parseLong(filter[0])),
                    BigDecimal.valueOf(Long.parseLong(filter[1])), pageable);
        }

        for (int i = 0; i < pageable.getPageSize(); i++) {
            pages.add(i);
        }
        return products;
    }

    public Product findProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Transactional
    public void saveProduct(Product product) {
        productRepository.save(product);
    }


    public List<Integer> getPages() {
        return pages;
    }


    public Page<Product> getProducts() {
        return products;
    }

    public Pageable getPageable() {
        return pageable;
    }

    public List<AdminURL> getAdminURL(){
        return adminURLRepository.findAll();
    }

    public List<AdminPanelBlock> getAdminBlocks(){
        return adminPanelBlockRepository.findAll();
    }


}
