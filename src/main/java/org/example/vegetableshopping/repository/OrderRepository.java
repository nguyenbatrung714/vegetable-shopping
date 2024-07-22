package org.example.vegetableshopping.repository;

import org.example.vegetableshopping.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    Page<Order> getOrdersByUser_UserIdOrderByOrderIdDesc(Integer userId, Pageable pageable);

}
