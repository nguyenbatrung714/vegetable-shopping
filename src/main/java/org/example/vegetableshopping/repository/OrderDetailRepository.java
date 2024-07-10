package org.example.vegetableshopping.repository;

import org.example.vegetableshopping.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

    List<OrderDetail> getOrderDetailsByOrder_OrderId(Integer orderId);

}
