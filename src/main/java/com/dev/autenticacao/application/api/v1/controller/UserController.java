package com.dev.autenticacao.application.api.v1.controller;

import com.dev.autenticacao.application.api.v1.dto.request.UserNewRequestDto;
import com.dev.autenticacao.application.api.v1.dto.response.UserResponseDto;
import com.dev.autenticacao.core.service.UserService;
import com.dev.autenticacao.infrastructure.util.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(path = "/v1/user", produces = "application/json")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

     /**
     * Somente usuarios autenticados podem acessar (Qualquer perfil)
     * @return lista de usuarios
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponseDto>>> allUsers() {
        return ResponseEntity.ok(ApiResponse.success(userService.allUsers()));
    }

    /**
     * Acesso liberado para usuarios autenticados com o perfil de administrador.
     * @param userNewRequestDto
     * @return
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Map<String, String>>> createUser(@RequestBody UserNewRequestDto userNewRequestDto) {
        userService.newUser(userNewRequestDto);
        Map<String, String> responseData = new HashMap<>();
        responseData.put("message", "Usuário Criado");

        return ResponseEntity.ok(ApiResponse.success(responseData));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDto>> userId(@PathVariable long id) {
        return ResponseEntity.ok(ApiResponse.success(userService.findById(id)));
    }

}
