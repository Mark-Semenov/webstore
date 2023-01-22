package ru.gb.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.store.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
