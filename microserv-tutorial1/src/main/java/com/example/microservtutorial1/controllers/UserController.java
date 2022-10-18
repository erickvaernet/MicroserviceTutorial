package com.example.microservtutorial1.controllers;

import com.example.microservtutorial1.entities.User;
import com.example.microservtutorial1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers (){
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById (@PathVariable int id){
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUserById (@PathVariable int id){
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<User> createUser (@Valid @RequestBody User user){
        return ResponseEntity.ok(userService.createUser(user));
    }

    /*
    @PutMapping("/{id}")
    public ResponseEntity<User> getUser (@PathVariable int id,@RequestBody User user){
        return ResponseEntity.ok();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> getUser (@PathVariable int id,@RequestBody User user){
        return ResponseEntity.ok();
    }*/

}
