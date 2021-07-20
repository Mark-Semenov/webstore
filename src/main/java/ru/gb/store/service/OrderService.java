package ru.gb.store.service;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.store.entities.Order;
import ru.gb.store.entities.OrderStatus;
import ru.gb.store.repositories.OrderRepository;

import java.util.Objects;

@Log4j2
@Data
@Component
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public void updateOrderStatus(String status, Long id) {
        Order order = orderRepository.save(Objects.requireNonNull(orderRepository.findById(id).orElse(null)));
        order.setStatus(OrderStatus.valueOf(status));
    }

}
