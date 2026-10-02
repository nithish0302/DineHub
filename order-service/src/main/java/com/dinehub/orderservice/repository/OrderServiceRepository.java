package com.dinehub.orderservice.repository;

import com.dinehub.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderServiceRepository extends JpaRepository<Order,Long> {
}
