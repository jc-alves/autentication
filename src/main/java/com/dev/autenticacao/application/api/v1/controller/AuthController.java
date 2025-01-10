package com.dev.autenticacao.application.api.v1.controller;

import com.dev.autenticacao.application.api.v1.dto.request.LoginRequestDto;
import com.dev.autenticacao.application.api.v1.dto.response.TokenResponse;
import com.dev.autenticacao.core.service.AuthService;
import com.dev.autenticacao.infrastructure.util.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/login", produces = "application/json")
public class AuthController {

   // private final UserService userService;
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping
    public ResponseEntity<ApiResponse<TokenResponse>> generationToken(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(
                ApiResponse.success(authService.authenticate(loginRequestDto.username(), loginRequestDto.password()))
        );
       //return ResponseEntity.ok( authService.authenticate(loginRequestDto.username(), loginRequestDto.password()));
    }

}
