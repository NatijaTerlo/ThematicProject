package com.example.thematicproject.controllers;

import com.example.thematicproject.models.Ingredient;
import com.example.thematicproject.models.Recipe;
import com.example.thematicproject.services.IngredientService;
import com.example.thematicproject.services.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*") // Tillader frontend kald fra browseren
@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;
    private final RecipeService recipeService;

    public IngredientController(IngredientService ingredientService, RecipeService recipeService) {
        this.ingredientService = ingredientService;
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable Long id) {
        return ingredientService.getIngredientById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Ingredient createIngredient(@RequestBody Ingredient ingredient) {
        return ingredientService.saveIngredient(ingredient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Long id) {
        ingredientService.deleteIngredient(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        List<Ingredient> ingredients = ingredientService.getAllIngredients();
        return ResponseEntity.ok(ingredients);
    }

    // ✅ OPDATERET: Finder opskrifter baseret på liste af ingredienser
    @PostMapping("/find")
    public ResponseEntity<List<Recipe>> findRecipes(@RequestBody IngredientRequest request) {
        List<String> ingredients = request.getIngredients();
        if (ingredients == null || ingredients.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Recipe> recipes = recipeService.findRecipesByIngredients(ingredients);
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    // 🔧 DTO til at modtage JSON fra frontend
    public static class IngredientRequest {
        private List<String> ingredients;

        public List<String> getIngredients() {
            return ingredients;
        }

        public void setIngredients(List<String> ingredients) {
            this.ingredients = ingredients;
        }
    }
}
