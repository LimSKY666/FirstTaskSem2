package ru.kpfu.itis.sokolov.service;

import ru.kpfu.itis.sokolov.dto.CreateUserDto;
import ru.kpfu.itis.sokolov.dto.UserDto;

import java.util.List;

import java.util.List;

public interface UserService {
    UserDto getByEmail(String email);

    UserDto getById(Integer id);

    List<UserDto> getAll();

    UserDto signUp(CreateUserDto createUserDto, String url);

    List<UserDto> getAllStepan();

    List<UserDto> getAllByName(String name);

    boolean verify(String verificationCode);

    void sendVerificationMail(String mail, String name, String code, String url);
}