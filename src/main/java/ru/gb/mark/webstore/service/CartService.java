package ru.gb.mark.webstore.service;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ru.gb.mark.webstore.entity.Order;
import ru.gb.mark.webstore.repository.CartRepository;
import ru.gb.mark.webstore.repository.OrderRepository;
import ru.gb.mark.webstore.session.UserSessionCart;

import java.math.BigDecimal;

@Log4j2
@Data
@Component
public class CartService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final UserSessionCart userSessionCart;
    private final ProductService productService;


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
