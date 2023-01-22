package ru.gb.mark.webstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.mark.webstore.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
