package com.dinehub.orderservice.client;

import com.dinehub.orderservice.dto.FoodDetails;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="food-service")
public interface FoodClient {
    @GetMapping("/foods/{foodId}")
    FoodDetails getFood(@PathVariable Long foodId);

}
