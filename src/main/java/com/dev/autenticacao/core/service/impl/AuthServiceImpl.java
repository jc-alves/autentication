package com.dev.autenticacao.core.service.impl;

import com.dev.autenticacao.application.api.v1.dto.response.TokenResponse;
import com.dev.autenticacao.core.exception.EventFullException;
import com.dev.autenticacao.core.repository.UserRepository;
import com.dev.autenticacao.core.service.AuthService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;

    public AuthServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtEncoder jwtEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
    }

    @Override
    public TokenResponse authenticate(String username, String password) {

        var  userResponse = userRepository.findUserByName(username);

        if (userResponse.isEmpty() || !passwordEncoder.matches(password, userResponse.get().getPassword())) {
            throw new EventFullException("Usuário ou senha inválidos!");
        }

        var user = userResponse.get();
        var now = Instant.now();
        var expiresIn = 3600L;

        List<String> roles = user.getRoles()
                .stream()
                .map(r -> r.getName())  // Adiciona o prefixo "ROLE_" corretamente
                .collect(Collectors.toList());    // Cada role será um item individual na lista

        var claims = JwtClaimsSet.builder()
                .issuer("API SysTech")
                .subject(userResponse.get().getId().toString())
                .claim("scope", roles)  // Passa a lista de roles separadas
                .expiresAt(now.plusSeconds(expiresIn))
                .build();

        return new TokenResponse(jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue());
    }

}
