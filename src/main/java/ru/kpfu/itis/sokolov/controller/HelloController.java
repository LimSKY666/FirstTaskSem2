package ru.kpfu.itis.sokolov.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import ru.kpfu.itis.sokolov.dto.CreateUserDto;


@Controller
public class HelloController {

    @Operation(summary = "Returns index page")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Page was get",
            content = {@Content(mediaType = "text/html")})})
    @GetMapping("")
    public String getIndexPage() {
        return "index";
    }

    @Operation(summary = "Returns sign up page")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Page was get",
            content = {@Content(mediaType = "text/html")})})
    @GetMapping("/sign_up")
    public String getSignUp(Model model) {
        model.addAttribute("user", new CreateUserDto());
        return "sign_up";
    }

    @Operation(summary = "Returns home page")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Page was get",
            content = {@Content(mediaType = "text/html")})})
    @GetMapping("/home")
    public String getHome() {
        return "home";
    }
}
