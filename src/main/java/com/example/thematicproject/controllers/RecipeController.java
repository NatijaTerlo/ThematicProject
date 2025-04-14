package com.example.thematicproject.controllers;

import com.example.thematicproject.models.Recipe;

import com.example.thematicproject.services.IngredientService;
import com.example.thematicproject.services.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:63342") // Allowed CORS for local frontend
@RestController
@RequestMapping("/recipes") // Base URL for all endpoints
public class RecipeController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    public RecipeController(RecipeService recipeService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    // GET: Fetch all recipes
    @GetMapping
    public List<Recipe> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    // GET: Fetch a recipe by ID
    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Long id) {
        return recipeService.getRecipeById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE: Delete a recipe by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        boolean isDeleted = recipeService.deleteRecipe(id); // Ensure delete logic in service layer
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/find")
    public ResponseEntity<List<Recipe>> findRecipes(@RequestBody IngredientRequest request) {
        List<String> ingredients = request.getIngredients();
        if (ingredients == null || ingredients.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Recipe> recipes = recipeService.findRecipesByIngredients(ingredients);
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }


    // POST: Create a new recipe
    @PostMapping
    public ResponseEntity<String> createRecipe(@RequestBody Recipe recipe) {
        recipeService.createRecipe(recipe);
        return new ResponseEntity<>("Recipe created successfully", HttpStatus.CREATED); // 201 Created
    }

    // GET: Test connection
    @GetMapping("/test")
    public String testConnection() {
        return "✅ Backend is up!";
    }
}
