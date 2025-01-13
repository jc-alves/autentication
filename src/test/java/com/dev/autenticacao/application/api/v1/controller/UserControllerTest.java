package com.dev.autenticacao.application.api.v1.controller;

import com.dev.autenticacao.application.api.v1.dto.request.UserNewRequestDto;
import com.dev.autenticacao.application.api.v1.dto.response.UserResponseDto;
import com.dev.autenticacao.core.service.UserService;
import com.dev.autenticacao.infrastructure.util.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test_shouldReturnListOfUsers() {

        UserResponseDto user1 = new UserResponseDto("User1", "user1@user1.com.br");
        UserResponseDto user2 = new UserResponseDto("User2", "user2@user2.com.br");
        List<UserResponseDto> mockUsers = Arrays.asList(user1, user2);
        when(userService.allUsers()).thenReturn(mockUsers);

        ResponseEntity<ApiResponse<List<UserResponseDto>>> result = userController.allUsers();

        assertEquals(2, result.getBody().getData().size());
        assertEquals("User1", result.getBody().getData().get(0).getUsername());
        assertEquals("User2", result.getBody().getData().get(1).getUsername());
        verify(userService, times(1)).allUsers();
    }

    @Test
    void createUser_shouldCallServiceAndReturnOk() {
        UserNewRequestDto mockRequest = new UserNewRequestDto();
        mockRequest.setUsername("New User");

        ResponseEntity<ApiResponse<Map<String, String>>> response = userController.createUser(mockRequest);

        assertEquals(200, response.getStatusCode().value());
        verify(userService, times(1)).newUser(mockRequest);
    }
}
