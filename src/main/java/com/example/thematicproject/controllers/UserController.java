package com.example.thematicproject.controllers;


import com.example.thematicproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Register new user
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestParam String username,
                                               @RequestParam String email,
                                               @RequestParam String password) {
        String result = userService.registerUser(username, email, password);
        return ResponseEntity.ok(result);
    }

    // Login user
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestParam String username,
                                            @RequestParam String password) {
        String result = userService.loginUser(username, password);
        return ResponseEntity.ok(result);
    }
}
