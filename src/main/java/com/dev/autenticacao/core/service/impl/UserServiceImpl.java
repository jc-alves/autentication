package com.dev.autenticacao.core.service.impl;

import com.dev.autenticacao.application.api.v1.dto.mapper.UserMapper;
import com.dev.autenticacao.application.api.v1.dto.request.UserNewRequestDto;
import com.dev.autenticacao.application.api.v1.dto.response.UserNameResponseDto;
import com.dev.autenticacao.core.domain.Users;
import com.dev.autenticacao.core.repository.UserRepository;
import com.dev.autenticacao.core.service.UserService;
import com.dev.autenticacao.core.util.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    private PasswordValidator passwordValidator;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public List<UserNameResponseDto> allUsers() {
        List<Users> users = userRepository.findAll();

        // Convertendo a lista de Users para UserNameResponseDto usando o mapper
        return users.stream()
                .map(userMapper::usersToNameResponseDto) // Mapeia cada Users para UserNameResponseDto
                .collect(Collectors.toList());
    }

    @Override
    public void newUser(UserNewRequestDto userNewRequestDto) {

        if(isExistingUser(userNewRequestDto.getUsername())) {
            throw new IllegalArgumentException("Usuario já cadastrado!");
        }

        if(isExistingEmail(userNewRequestDto.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado!");
        }

        if (!passwordValidator.validate(userNewRequestDto.getPassword())) {
            throw new IllegalArgumentException("Senha inválida. Deve ser forte.");
        }

        Users user = userMapper.userNemRequestToUsers(userNewRequestDto);
        user.setPassword(bCryptPasswordEncoder.encode(userNewRequestDto.getPassword()));

        userRepository.save(user);
    }

    private boolean isExistingUser(String nameUser) {

        return userRepository.findUserByName(nameUser).isPresent();
    }

    private boolean isExistingEmail(String email) {

        return userRepository.findUserByEmail(email).isPresent();
    }
}
