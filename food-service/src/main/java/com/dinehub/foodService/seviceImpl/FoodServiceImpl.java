package com.dinehub.foodService.seviceImpl;

import com.dinehub.foodService.repo.FoodRepository;
import com.dinehub.foodService.entity.Food;
import com.dinehub.foodService.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;

    @Override
    public Food createFood(Food food) {
        return foodRepository.save(food);
    }

    @Override
    public Food getFoodById(Long foodId) {
        return foodRepository.findById(foodId)
                .orElseThrow(() -> new RuntimeException("Food not found"));
    }

    @Override
    public List<Food> getAllFood() {
        return foodRepository.findAll();
    }

    @Override
    public Food updateFood(Long foodId, Food food) {

        Food existingFood = foodRepository.findById(foodId)
                .orElseThrow(() -> new RuntimeException("Food not found"));

        existingFood.setName(food.getName());
        existingFood.setDescription(food.getDescription());
        existingFood.setPrice(food.getPrice());
        existingFood.setQuantity(food.getQuantity());

        existingFood.setImageUrl(food.getImageUrl());
        existingFood.setIsAvailable(food.getIsAvailable());

        return foodRepository.save(existingFood);
    }

    @Override
    public void deleteFood(Long foodId) {

        if (!foodRepository.existsById(foodId)) {
            throw new RuntimeException("Food not found");
        }

        foodRepository.deleteById(foodId);
    }
}