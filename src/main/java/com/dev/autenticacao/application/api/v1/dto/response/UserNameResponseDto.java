package com.dev.autenticacao.application.api.v1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class UserNameResponseDto {
    private String username;
    private String email;
    private String password;
}
