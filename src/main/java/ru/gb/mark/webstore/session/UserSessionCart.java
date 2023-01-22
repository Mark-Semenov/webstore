package ru.gb.mark.webstore.session;

import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import ru.gb.mark.webstore.entity.Product;
import ru.gb.mark.webstore.service.ProductService;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Log4j2
@Data
@Component
@SessionScope
public class UserSessionCart {

    private final ProductService productService;
    private Map<Product, Integer> productCart;
    private Integer count = 0;
    private Integer discount = 0;
    private Integer prodDiscount = 0;
    private BigDecimal totalSum;

    @PostConstruct
    public void init() {
        productCart = new HashMap<>();
        totalSum = new BigDecimal(0);
    }

    public void addToCart(Long prodId) {
        Product product;
        if (getProductInsideCart(prodId) == null) {
            count = 0;
            product = productService.findProductById(prodId);
        } else {
            product = getProductInsideCart(prodId);
            count = productCart.get(product);
        }
        prodDiscount = calculateProductDiscount(product);
        incrementTotalDiscountAndGet(prodDiscount);
        totalSum = totalSum.add(product.getPrice());
        productCart.put(product, incrementCountAndGet());
    }

    public void removeProdCompletely(Long prodId) {
        Product product = getProductInsideCart(prodId);
        prodDiscount = calculateProductDiscount(product);
        decrementTotalDiscount(prodDiscount, productCart.get(product));
        int sum;
        if (!totalSum.equals(BigDecimal.valueOf(0))) {
            sum = (product.getPrice().intValue() * productCart.get(product));
            totalSum = totalSum.subtract(BigDecimal.valueOf(sum));
        }
        productCart.remove(product);

    }

    public void removeOneProd(Long prodId) {
        Product product = getProductInsideCart(prodId);
        count = productCart.get(product);
        prodDiscount = calculateProductDiscount(product);
        if (count != 1) {
            productCart.replace(product, decrementCountAndGet());
            decrementTotalDiscount(prodDiscount, 1);
            if (!totalSum.equals(BigDecimal.valueOf(0))) {
                totalSum = totalSum.subtract(product.getPrice());
            }
        }
    }

    private Product getProductInsideCart(@NotNull Long prodId) {
        Product product;
        product = productCart.keySet()
                .stream()
                .filter(p -> p.getId().equals(prodId))
                .findFirst().orElse(null);

        return product;
    }

    public Integer incrementCountAndGet() {
        return ++count;
    }

    public Integer decrementCountAndGet() {
        return --count;
    }

    public void incrementTotalDiscountAndGet(Integer prodDiscount) {
        discount += prodDiscount;
    }

    public void decrementTotalDiscount(Integer prodDiscount, Integer count) {
        discount -= (prodDiscount * count);
    }

    public int calculateProductDiscount(Product p) {
        return p.getOldPrice() != null ? p.getOldPrice().subtract(p.getPrice()).intValue() : 0;

    }

    public Integer calculateTotalDiscount() {
        discount = 0;
        for (Product p : productCart.keySet()) {
            prodDiscount = calculateProductDiscount(p);
            discount += (prodDiscount * productCart.get(p));
        }
        return discount;
    }

    public void calculateTotalSum() {
        int sum = 0;
        for (Product p : productCart.keySet()) {
            sum += (p.getPrice().intValue() * productCart.get(p));
            totalSum = BigDecimal.valueOf(sum);
        }
    }

    public Integer getProductsCount() {
        AtomicReference<Integer> totalCount = new AtomicReference<>(0);
        productCart.values().forEach(integer -> totalCount.updateAndGet(v -> v + integer));
        return totalCount.get();
    }


}
