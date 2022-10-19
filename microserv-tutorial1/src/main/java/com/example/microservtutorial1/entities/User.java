package com.example.microservtutorial1.entities;


import com.fasterxml.jackson.annotation.JsonFilter;

import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@JsonFilter("filtroUsuario")
public class User {
    private Integer id;
    @Size(min = 2, message = "Name must have at least 2 characters")
    private String name;
    @PastOrPresent(message = "birthDate must be before that the current date")
    private LocalDate birthDate;

    public User() {
    }

    public User(Integer id, String name, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
