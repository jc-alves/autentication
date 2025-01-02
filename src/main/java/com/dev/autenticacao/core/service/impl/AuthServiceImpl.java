package com.dev.autenticacao.core.service.impl;

import com.dev.autenticacao.application.api.v1.dto.response.TokenResponse;
import com.dev.autenticacao.application.api.v1.dto.response.UserNameResponseDto;
import com.dev.autenticacao.core.exception.EventFullException;
import com.dev.autenticacao.core.repository.UserRepository;
import com.dev.autenticacao.core.service.AuthService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.dev.autenticacao.core.config.JwtTokenProvider;

import java.util.Optional;


@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public TokenResponse authenticate(String username, String password) {
        Optional<UserNameResponseDto> userResponse = userRepository.findUserByName(username)
                .map(user -> new UserNameResponseDto(user.getUsername(), user.getEmail(), user.getPassword()));

        if (userResponse.isEmpty() || !passwordEncoder.matches(password, userResponse.get().getPassword())) {
            throw new EventFullException("Usuário ou senha inválidos!");
        }
        String teste = jwtTokenProvider.generateToken(userResponse.get().getUsername());

        return new TokenResponse(jwtTokenProvider.generateToken(userResponse.get().getUsername()));
    }


}
