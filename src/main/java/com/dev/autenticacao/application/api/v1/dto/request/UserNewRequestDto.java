package com.dev.autenticacao.application.api.v1.dto.request;


import lombok.Data;

@Data
public class UserNewRequestDto {

    private String username;
    private String password;
    private String email;
}
