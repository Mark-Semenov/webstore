package ru.gb.store.session;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.store.entities.Cart;
import ru.gb.store.entities.Product;
import ru.gb.store.service.CartService;
import ru.gb.store.service.UserService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Data
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserSessionCart {

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

    public Integer incrementDiscountAndGet(Integer prodDiscount) {
        return discount += prodDiscount;
    }

    public Integer calculateDiscountAndGet(Integer prodDiscount, Integer count) {
        return discount -= (prodDiscount * count);
    }


    public Integer decrementCountAndGet() {
        return --count;
    }

    public Integer incrementCountAndGet() {
        return ++count;
    }


}
