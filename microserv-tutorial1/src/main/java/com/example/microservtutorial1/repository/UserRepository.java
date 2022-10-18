package com.example.microservtutorial1.repository;

import com.example.microservtutorial1.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findUserById(int id);
    List<User> findAllUsers();
    User createUser(User newUser);
    void deleteUser(int id);
    User updateUserPut(int id, User newUser);
    User updateUserPatch(int id, User newUser);
}
