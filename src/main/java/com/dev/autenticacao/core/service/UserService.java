package com.dev.autenticacao.core.service;

import com.dev.autenticacao.application.api.v1.dto.request.UserNewRequestDto;
import com.dev.autenticacao.application.api.v1.dto.response.UserNameResponseDto;
import com.dev.autenticacao.application.api.v1.dto.response.UserResponseDto;

import java.util.List;

public interface UserService {

    List<UserResponseDto> allUsers();

    void newUser(UserNewRequestDto userNewRequestDto);
}
