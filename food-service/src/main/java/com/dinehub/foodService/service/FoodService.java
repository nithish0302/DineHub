package com.dinehub.foodService.service;

import com.dinehub.foodService.entity.Food;

import java.util.List;

public interface FoodService {

    Food createFood(Food food);

    Food getFoodById(Long foodId);

    List<Food> getAllFood();

    Food updateFood(Long foodId, Food food);

    void deleteFood(Long foodId);
}