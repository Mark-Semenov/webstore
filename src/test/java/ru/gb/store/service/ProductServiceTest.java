package ru.gb.store.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.gb.store.entities.Product;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    void findByName() {
        Product product = productService.findByName("samsung").iterator().next();
        Assertions.assertThat(product.getName().equals("Samsung Galaxy U21 Ultra")).isTrue();
    }

    @Test
    void findProductById() {
        Product product = productService.findProductById(1L);
        org.assertj.core.api.Assertions.assertThat(product).isNotNull();
    }
}