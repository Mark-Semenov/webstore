package ru.gb.store.service;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ru.gb.store.session.UserSessionCart;
import ru.gb.store.entities.Cart;
import ru.gb.store.entities.Order;
import ru.gb.store.entities.Product;
import ru.gb.store.repositories.CartRepository;
import ru.gb.store.repositories.OrderRepository;

@Log4j2
@Data
@Component
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final UserSessionCart userSessionCart;


    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    public void saveUserCart(Cart cart) {
        cartRepository.save(cart);
    }

    public void addProduct(@NonNull Product product) {
        if (userSessionCart.getProductCart().stream().noneMatch(product1 -> product1.equals(product))) {
            userSessionCart.getProductCart().add(product);
        }
        userSessionCart.setTotalSum(userSessionCart.getTotalSum().add(product.getPrice()));
        log.info("Products count in the cart: " + userSessionCart.getProductCart().size());
        log.info("Total sum: " + userSessionCart.getTotalSum());

    }

    public Product getProductById(Long prodId) {
        return userSessionCart.getProductCart().stream().filter(product1 -> product1.getId().equals(prodId)).iterator().next();
    }

    public void deleteProduct(@NotNull Product product) {
        userSessionCart.getProductCart().remove(product);
        userSessionCart.setTotalSum(userSessionCart.getTotalSum().subtract(product.getPrice()));
        log.info("Products count in the cart: " + userSessionCart.getProductCart().size());
        log.info("Total sum: " + userSessionCart.getTotalSum());
    }

    public void buyProducts(Order order) {
        order.setTotalSum(userSessionCart.getTotalSum());
        saveOrder(order);
        userSessionCart.getProductCart().clear();
    }


}
