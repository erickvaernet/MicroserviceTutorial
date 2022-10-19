package com.example.microservtutorial1.controllers;

import com.example.microservtutorial1.entities.User;
import com.example.microservtutorial1.services.UserService;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final  MessageSource messageSource;

    @Autowired
    public UserController(UserService userService, MessageSource messageSource) {
        this.userService = userService;
        this.messageSource = messageSource;
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers1 (){
        return ResponseEntity.ok(userService.findAllUsers());
    }
    //Versionado por header:
    @GetMapping(headers = "X-API-VERSION=2")
    public ResponseEntity<List<User>> getUsers2 (){
        return ResponseEntity.ok(userService.findAllUsers().stream()
                .peek((e)->e.setName(e.getName()+"-V2")).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById (@PathVariable int id){
        User user = userService.findUserById(id);
        //Usamos un Wrapper?
        MappingJacksonValue userWrapper =  new MappingJacksonValue(user);
        //Creamos el filtro en este caso que solo se vea el nombre
        SimpleBeanPropertyFilter filtro =
                SimpleBeanPropertyFilter.filterOutAllExcept("name");
        //Loasociamos al jacksonfilter 'filtroUsuario'
        FilterProvider filterProvider =
                new SimpleFilterProvider().addFilter("filtroUsuario",filtro);
        //Lo agregamos al wrapper para que surja efecto
        userWrapper.setFilters(filterProvider);
        return ResponseEntity.ok(userWrapper);
    }

    //Usando HATEOAS
    @GetMapping("/hateoas/{id}")
    public ResponseEntity<EntityModel<User>> getUserByIdHateoas (@PathVariable int id){
        //Buscamos el Usuario segun Id
        User user =  userService.findUserById(id);
        //Creamos Modelo de User
        EntityModel<User> userModel = EntityModel.of(user);
        //Creamos el link al método getUsers1() con el builder
        WebMvcLinkBuilder linkBuilderAllUsers = WebMvcLinkBuilder.linkTo(
          WebMvcLinkBuilder.methodOn(this.getClass()).getUsers1()
        );
        //Agregamos el nombre asociado al link
        Link linkCompletoGetUsers = linkBuilderAllUsers.withRel("getAllUsersV1");
        //Agregamos el link con nombre al modelo
        userModel.add(linkCompletoGetUsers);
        //retornamos el response entity con el modelo
        return ResponseEntity.ok(userModel);
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

    @GetMapping("/global-message")
    public ResponseEntity<String> getGlobalMessage (){
        Locale locale= LocaleContextHolder.getLocale();
        String message =
                messageSource.getMessage(
                        "hello.world.message",
                        null,
                        "Default message hello world",
                        locale
                );
        return ResponseEntity.ok(message);
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
