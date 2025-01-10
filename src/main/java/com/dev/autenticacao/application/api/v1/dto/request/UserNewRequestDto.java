package com.dev.autenticacao.application.api.v1.dto.request;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class UserNewRequestDto {

    private String username;
    private String password;
    private String email;
}
