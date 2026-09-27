package com.dinehub.foodService.controller;

import com.dinehub.foodService.entity.Food;
import com.dinehub.foodService.service.FoodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @PostMapping
    public ResponseEntity<Food> createFood( @Valid @RequestBody Food food) {

        return new ResponseEntity<>(
                foodService.createFood(food),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{foodId}")
    public ResponseEntity<Food> getFoodById(
            @PathVariable Long foodId) {

        return ResponseEntity.ok(
                foodService.getFoodById(foodId)
        );
    }

    @GetMapping
    public ResponseEntity<List<Food>> getAllFood() {

        return ResponseEntity.ok(
                foodService.getAllFood()
        );
    }

    @PutMapping("/{foodId}")
    public ResponseEntity<Food> updateFood(
            @PathVariable Long foodId,
            @RequestBody Food food) {

        return ResponseEntity.ok(
                foodService.updateFood(foodId, food)
        );
    }

    @DeleteMapping("/{foodId}")
    public ResponseEntity<Void> deleteFood(
            @PathVariable Long foodId) {

        foodService.deleteFood(foodId);

        return ResponseEntity.noContent().build();
    }
}