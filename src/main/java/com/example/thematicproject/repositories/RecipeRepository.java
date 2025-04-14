package com.example.thematicproject.repositories;



import com.example.thematicproject.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findAllById(Long id);

    List<Recipe> findByIngredientsIn(List<Long> ingredients);
}

