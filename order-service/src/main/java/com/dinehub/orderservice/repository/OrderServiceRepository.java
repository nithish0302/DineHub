package com.dinehub.orderservice.repository;

import com.dinehub.orderservice.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderServiceRepository extends MongoRepository<Order,Long> {

    List<Order> findByUserIdOrderByCreatedAtDesc(Long userId);
}
