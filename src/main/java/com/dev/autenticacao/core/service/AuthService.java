package com.dev.autenticacao.core.service;

import com.dev.autenticacao.application.api.v1.dto.response.TokenResponse;

public interface AuthService {
    TokenResponse authenticate(String username, String password);
}
