package com.dinehub.foodService.service.seviceImpl;

import com.dinehub.foodService.exception.FoodNotFoundException;
import com.dinehub.foodService.repository.FoodRepository;
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
                .orElseThrow(() ->
                        new FoodNotFoundException(
                                "Food not found with id: " + foodId
                        )
                );
    }

    @Override
    public List<Food> getAllFood() {
        return foodRepository.findAll();
    }

    @Override
    public Food updateFood(Long foodId, Food food) {

        Food existingFood = getFoodById(foodId);

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
            throw new FoodNotFoundException(
                    "Food not found with id: " + foodId
            );
        }

        foodRepository.deleteById(foodId);
    }

    @Override
    public void updateFoodCount(Long foodId, Integer neededQuantity) {
        Food existingFood = getFoodById(foodId);
        existingFood.setQuantity(existingFood.getQuantity()-neededQuantity);
        foodRepository.save(existingFood);
    }
}