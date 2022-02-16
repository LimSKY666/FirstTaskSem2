package com.sokolov.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class CreateUserDto {

    @NotBlank(message = "Name shouldn't be blank!")
    private String name;

    @Email
    @NotBlank(message = "Email shouldn't be blank!")
    private String email;

    @Length(min = 5, message = "Password should contains at least 5 symbols")
    @NotBlank(message = "Password shouldn't be blank!")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CreateUserDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
