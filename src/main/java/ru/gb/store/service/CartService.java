package ru.gb.store.service;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ru.gb.store.entities.Order;
import ru.gb.store.repositories.CartRepository;
import ru.gb.store.repositories.OrderRepository;
import ru.gb.store.session.UserSessionCart;

import java.math.BigDecimal;

@Log4j2
@Data
@Component
public class CartService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final UserSessionCart userSessionCart;
    private final ProductService productService;
    private final UserService userService;


    public void buyProducts(Order order) {
        order.setTotalSum(userSessionCart.getTotalSum());
        saveOrder(order);
        userSessionCart.getProductCart().clear();
        userSessionCart.setTotalSum(BigDecimal.valueOf(0));
        userSessionCart.setDiscount(0);
    }

    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    public void addToCart(@NonNull Long prodId) {
        userSessionCart.addToCart(prodId);
    }

    public void deleteProduct(@NotNull Long prodId) {
        userSessionCart.removeProdCompletely(prodId);
    }

    public void removeOne(Long prodId) {
        userSessionCart.removeOneProd(prodId);
    }

    public Integer getProductsCount() {
        return userSessionCart.getProductsCount();
    }


}
