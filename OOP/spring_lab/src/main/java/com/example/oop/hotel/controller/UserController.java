package com.example.oop.hotel.controller;

import com.example.oop.hotel.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;


@Slf4j
@Controller
@CrossOrigin(origins = "http://localhost:4200")

public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }



}
