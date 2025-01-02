package com.dev.autenticacao.core.service;

import com.dev.autenticacao.application.api.v1.dto.response.TokenResponse;
import com.dev.autenticacao.application.api.v1.dto.response.UserNameResponseDto;

import java.util.Optional;

public interface AuthService {
    TokenResponse authenticate(String username, String password);
}
