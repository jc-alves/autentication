package com.dev.autenticacao.application.api.v1.controller;

import com.dev.autenticacao.application.api.v1.dto.request.UserNewRequestDto;
import com.dev.autenticacao.application.api.v1.dto.response.UserNameResponseDto;
import com.dev.autenticacao.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping(path = "/v1/user", produces = "application/json")
@RestController
public class UserController {


    @Autowired
    private UserService userService;



    @GetMapping
    public List<UserNameResponseDto> test() {
        List<UserNameResponseDto> users = new ArrayList<>();
        users = userService.allUsers();


        return users;
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserNewRequestDto userNewRequestDto) {

        try {
            userService.newUser(userNewRequestDto);

            return ResponseEntity.ok().build();
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); //badRequest().body(e.getMessage());

        }



    }


}
