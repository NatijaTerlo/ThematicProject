package com.example.thematicproject.controllers;


import com.example.thematicproject.models.Recipe;
import com.example.thematicproject.services.IngredientService;
import com.example.thematicproject.services.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@CrossOrigin(origins = "http://localhost:63342") // Adjust to match your frontend URL
@RestController
@RequestMapping("/recipes")
public class RecipeController {
    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    public RecipeController(RecipeService recipeService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @GetMapping
    public List<Recipe> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Long id) {
        return recipeService.getRecipeById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Long id) {
        ingredientService.deleteIngredient(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/find")
    public ResponseEntity<List<Recipe>> findRecipes(@RequestBody List<String> ingredients) {
        if (ingredients == null || ingredients.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 400 if ingredients are empty
        }

        try {
            List<Recipe> recipes = recipeService.findRecipesByIngredients(ingredients);
            if (recipes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 if no recipes found
            }
            return new ResponseEntity<>(recipes, HttpStatus.OK); // 200 OK if recipes found
        } catch (Exception e) {
            // Log the error to track the issue
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 if something went wrong
        }
    }


    @PostMapping
    public ResponseEntity<String> createRecipe(@RequestBody Recipe recipe) {
        // Logic for creating a new recipe
        return ResponseEntity.ok("Recipe created successfully");
    }
}
