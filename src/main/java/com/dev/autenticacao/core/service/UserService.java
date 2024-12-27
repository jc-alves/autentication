package com.dev.autenticacao.core.service;

import com.dev.autenticacao.application.api.v1.dto.request.UserNewRequestDto;
import com.dev.autenticacao.application.api.v1.dto.response.UserNameResponseDto;

import java.util.List;

public interface UserService {

    List<UserNameResponseDto> allUsers();

    void newUser(UserNewRequestDto userNewRequestDto);
}
