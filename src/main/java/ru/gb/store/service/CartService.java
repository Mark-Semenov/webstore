package ru.gb.store.service;

import com.sun.istack.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ru.gb.store.dto.CartDTO;
import ru.gb.store.entities.Cart;
import ru.gb.store.entities.Order;
import ru.gb.store.entities.Product;
import ru.gb.store.repositories.CartRepository;
import ru.gb.store.repositories.OrderRepository;

@Log4j2
@Component
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final CartDTO cartDTO;


    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    public void saveUserCart(Cart cart) {
        cartRepository.save(cart);
    }

    public void addProduct(@NonNull Product product) {
        cartDTO.getProductCart().add(product);
        cartDTO.setTotalSum(cartDTO.getTotalSum().add(product.getPrice()));
        log.info("Products count in the cart: " + cartDTO.getProductCart().size());
        log.info("Total sum: " + cartDTO.getTotalSum());

    }

    public Product getProductById(Long prodId) {
        return cartDTO.getProductCart().stream().filter(product1 -> product1.getId().equals(prodId)).iterator().next();
    }

    public void deleteProduct(@NotNull Product product) {
        cartDTO.getProductCart().remove(product);
        cartDTO.setTotalSum(cartDTO.getTotalSum().subtract(product.getPrice()));
        log.info("Products count in the cart: " + cartDTO.getProductCart().size());
        log.info("Total sum: " + cartDTO.getTotalSum());
    }

    public void buyProducts(Order order) {
        order.setTotalSum(cartDTO.getTotalSum());
        saveOrder(order);
        cartDTO.getProductCart().clear();
    }


}
