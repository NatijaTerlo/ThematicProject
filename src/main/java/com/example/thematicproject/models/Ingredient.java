package com.example.thematicproject.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity  // Add this annotation to mark this as a JPA entity
public class Ingredient {

    @Id  // Assuming you have an identifier for Ingredient
    private Long id;
    private String name;
    private String quantity;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
