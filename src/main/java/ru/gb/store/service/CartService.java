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

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

@Log4j2
@Data
@Component
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final UserSessionCart userSessionCart;
    private final ProductService productService;
    private Integer count = 0;
    private Integer discount = 0;
    private Integer prodDiscount = 0;

    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    public void saveUserCart(Cart cart) {
        cartRepository.save(cart);
    }

    public void addToCart(@NonNull Long prodId) {
        Product product;
        if (getProductInsideCart(prodId) == null) {
            count = 0;
            product = productService.findProductById(prodId);
        } else product = getProductInsideCart(prodId);
        count = userSessionCart.getProductCart().get(product) != null ? userSessionCart.getProductCart().get(product) : 0;
        prodDiscount = calculateDiscount(product);
        discount += prodDiscount;
        userSessionCart.getProductCart().put(product, ++count);
        userSessionCart.setTotalSum(userSessionCart.getTotalSum().add(product.getPrice()));
    }

    public void deleteProduct(@NotNull Long prodId) {
        Product product = getProductInsideCart(prodId);
        prodDiscount = calculateDiscount(product);
        count = userSessionCart.getProductCart().get(product);
        discount -= (prodDiscount*count);
        userSessionCart.getProductCart().remove(product);
        userSessionCart.setTotalSum(userSessionCart.getTotalSum().subtract(product.getPrice().multiply(BigDecimal.valueOf(count))));

    }

    public void removeOne(Long prodId) {
        Product product = getProductInsideCart(prodId);
        prodDiscount = calculateDiscount(product);
        count = userSessionCart.getProductCart().get(product);
        if (count != 1) {
            userSessionCart.getProductCart().replace(product, --count);
            discount -= prodDiscount;
            if (!userSessionCart.getTotalSum().equals(BigDecimal.valueOf(0))) {
                userSessionCart.setTotalSum(userSessionCart.getTotalSum().subtract(product.getPrice()));
            }
        }
    }

    private Product getProductInsideCart(@NotNull Long prodId) {
        Product product = null;
        if (!userSessionCart.getProductCart().isEmpty()) {
            product = userSessionCart.getProductCart().keySet()
                    .stream()
                    .filter(p -> p.getId().equals(prodId))
                    .findFirst().orElse(null);
        }
        return product;
    }

    public void buyProducts(Order order) {
        order.setTotalSum(userSessionCart.getTotalSum());
        saveOrder(order);
        userSessionCart.getProductCart().clear();
    }

    public int calculateDiscount(Product p) {
        int discount = 0;
        discount += p.getOldPrice() != null ? p.getOldPrice().subtract(p.getPrice()).intValue() : 0;
        return discount;
    }

    public Integer getProductsCount() {
        AtomicReference<Integer> totalCount = new AtomicReference<>(0);
        userSessionCart.getProductCart().values().forEach(integer -> totalCount.updateAndGet(v -> v + integer));
        return totalCount.get();
    }
}
