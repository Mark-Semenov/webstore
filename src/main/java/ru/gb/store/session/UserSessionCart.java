package ru.gb.store.session;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import ru.gb.store.entities.Product;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Log4j2
@Data
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserSessionCart {

    private List<Product> productCart;
    private Map<Product, Integer> productCount;
    private Integer count = 0;
    private BigDecimal totalSum;

    @PostConstruct
    public void init() {
        productCart = new ArrayList<>();
        totalSum = new BigDecimal(0);

    }

}
