package com.example.dscommerce.repositories;

import com.example.dscommerce.entities.OrderItem;
import com.example.dscommerce.entities.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {
}
