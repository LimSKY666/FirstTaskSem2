package ru.kpfu.itis.sokolov.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.sokolov.dto.CreateUserDto;
import ru.kpfu.itis.sokolov.dto.UserDto;
import ru.kpfu.itis.sokolov.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Returns all users")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Users were get",
            content = {@Content(mediaType = "application/json")})})
    @GetMapping("/user")
    @ResponseBody
    public Iterable<UserDto> getAll(@RequestParam(value = "name", required = false) Optional<String> name) {
        return name.isEmpty() ? userService.getAll() : userService.getAllByName(name.get());
    }

    @Operation(summary = "Returns user by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Users was get by id",
            content = {@Content(mediaType = "application/json")})})
    @GetMapping("/user/{id}")
    @ResponseBody
    public UserDto getUser(@PathVariable Integer id) {
        return userService.getById(id);
    }


//    @PostMapping("/user")
//    @ResponseBody
//    public UserDto createUser(@Valid @RequestBody CreateUserDto user) {
//        return userService.signUp(user);
//    }


    @Operation(summary = "Returns sign up success page")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Page was get",
            content = {@Content(mediaType = "text/html")})})
    @PostMapping("/sign_up")
    public String signUp(@ModelAttribute(name = "user") CreateUserDto userDto, HttpServletRequest request) {
        String url = request.getRequestURL().toString().replace(request.getServletPath(), "");
        userService.signUp(userDto, url);
        return "sign_up_success";
    }

    @Operation(summary = "Returns verification page")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Page was get",
            content = {@Content(mediaType = "text/html")})})
    @GetMapping("/verify")
    public String verify(@Param("code") String code) {
        if (userService.verify(code)) {
            return "verification_success";
        } else {
            return "verification_failed";
        }
    }
}
