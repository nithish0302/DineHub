package com.dinehub.orderservice.client;

import com.dinehub.orderservice.dto.FoodDetails;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name="food-service")
public interface FoodClient {
    @GetMapping("/api/foods/get/{foodId}")
    FoodDetails getFood(@PathVariable Long foodId);

    @PutMapping("api/foods/update/foodCount/{foodId}")
    Void updateFoodCount(@PathVariable Long foodId,Integer neededQuantity);
}
