package com.example.thematicproject.services;


import com.example.thematicproject.models.Recipe;
import com.example.thematicproject.repositories.RecipeRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public Optional<Recipe> getRecipeById(Long id) {
        return recipeRepository.findById(id);
    }

    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public boolean deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
        return false;
    }

    public List<Recipe> findRecipesByIngredients(List<String> ingredients) {
        // Check if each ingredient is a valid number
        List<Long> validIngredientIds = ingredients.stream()
                .filter(ingredient -> isValidIngredient(ingredient))
                .map(Long::valueOf) // This will throw an exception if the string is not a valid number
                .collect(Collectors.toList());

        return recipeRepository.findByIngredientsIn(validIngredientIds);
    }

    private boolean isValidIngredient(String ingredient) {
        try {
            Long.parseLong(ingredient); // This will throw an exception for non-numeric values
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    public Recipe createRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }
}

