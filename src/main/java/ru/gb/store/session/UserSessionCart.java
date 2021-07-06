package ru.gb.store.session;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import ru.gb.store.entities.Product;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Data
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserSessionCart {

    private Map<Product, Integer> productCart;
    private Integer count = 0;
    private BigDecimal totalSum;

    @PostConstruct
    public void init() {
        productCart = new HashMap<>();
        totalSum = new BigDecimal(0);

    }

}
