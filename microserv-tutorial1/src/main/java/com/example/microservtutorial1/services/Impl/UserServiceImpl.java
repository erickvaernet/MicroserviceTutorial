package com.example.microservtutorial1.services.Impl;

import com.example.microservtutorial1.Exceptions.UserNotFoundException;
import com.example.microservtutorial1.entities.User;
import com.example.microservtutorial1.repository.UserRepository;
import com.example.microservtutorial1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl  implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findUserById(int id) {
        return userRepository.findUserById(id)
                .orElseThrow(()->new UserNotFoundException("User not found"));
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAllUsers();
    }

    @Override
    public User createUser(User newUser) {
        return userRepository.createUser(newUser);
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteUser(id);
    }

    @Override
    public User updateUserPut(int id, User newUser) {
        return null;
    }

    @Override
    public User updateUserPatch(int id, User newUser) {
        return null;
    }
}
