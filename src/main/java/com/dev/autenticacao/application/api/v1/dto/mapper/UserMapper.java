package com.dev.autenticacao.application.api.v1.dto.mapper;

import com.dev.autenticacao.application.api.v1.dto.request.UserNewRequestDto;
import com.dev.autenticacao.application.api.v1.dto.response.UserNameResponseDto;
import com.dev.autenticacao.application.api.v1.dto.response.UserResponseDto;
import com.dev.autenticacao.core.domain.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserNameResponseDto usersToNameResponseDto(Users users);
    UserResponseDto usersToResponseDto(Users users);
    Users userNemRequestToUsers(UserNewRequestDto userNewRequestDto);

}
