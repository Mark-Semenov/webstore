package ru.gb.store.session;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.gb.store.entities.Product;
import ru.gb.store.service.ProductService;

import javax.annotation.PostConstruct;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserSessionCartTest {

    private Product product;

    @Autowired
    private UserSessionCart userSessionCart;


    @Autowired
    private ProductService productService;

    @Test
    void test(){
        Assertions.assertThat(userSessionCart).isNotNull();
    }

    @PostConstruct
    void initCart() {
        product = productService.findProductById(1L);
        userSessionCart.addToCart(1L);
        userSessionCart.addToCart(1L);
        userSessionCart.addToCart(1L);
        userSessionCart.addToCart(2L);
    }

    @Test
    void addToCart() {
        userSessionCart.addToCart(3L);
        Assertions.assertThat(userSessionCart.getProductCart().size()).isEqualTo(3);
    }

    @Test
    void removeProdCompletely() {
        userSessionCart.removeProdCompletely(1L);
        Assertions.assertThat(userSessionCart.getProductCart().containsKey(product)).isFalse();
    }

    @Test
    void removeOneProd() {
        userSessionCart.removeOneProd(1L);
        Assertions.assertThat(userSessionCart.getProductCart().get(product)).isEqualTo(2);
    }

    @Test
    void calculateTotalSum() {
        Assertions.assertThat(userSessionCart.getTotalSum().intValue()).isEqualTo(5950);
    }

    @Test
    void calculateTotalDiscount() {
        Assertions.assertThat(userSessionCart.calculateTotalDiscount().intValue()).isEqualTo(775);
    }

    @Test
    void calculateProductDiscount() {
        Assertions.assertThat(userSessionCart.calculateProductDiscount(product)).isEqualTo(225);
    }
}