package com.example.microservtutorial1.repository;

import com.example.microservtutorial1.entities.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserDao implements UserRepository{

    private static List<User> users;
    private static int id = 0;

    static{
        users=new ArrayList<>();
        users.add(new User(getNextId(),"Erick", LocalDate.now()));
        users.add(new User(getNextId(),"Ian", LocalDate.now()));
        users.add(new User(getNextId(),"Ivan", LocalDate.now()));
    }

    private static int getNextId(){
        return ++id;
    }

    @Override
    public Optional<User> findUserById(int id) {
        return users.stream().filter((user)->user.getId().equals(id)).findFirst();
    }

    @Override
    public List<User> findAllUsers() {
        return users;
    }

    @Override
    public User createUser(User newUser) {
        if( newUser.getId() == null ) newUser.setId( getNextId() );
        users.add(newUser);
        return newUser;
    }

    @Override
    public void deleteUser(int id) {
        users.removeIf((user)->user.getId().equals(id));
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
