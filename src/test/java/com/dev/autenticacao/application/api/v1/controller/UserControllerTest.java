package com.dev.autenticacao.application.api.v1.controller;

import com.dev.autenticacao.application.api.v1.dto.request.UserNewRequestDto;
import com.dev.autenticacao.application.api.v1.dto.response.UserNameResponseDto;
import com.dev.autenticacao.core.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

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

        UserNameResponseDto user1 = new UserNameResponseDto("User1", "user1@user1.com.br");
        UserNameResponseDto user2 = new UserNameResponseDto("User2", "user2@user2.com.br");
        List<UserNameResponseDto> mockUsers = Arrays.asList(user1, user2);
        when(userService.allUsers()).thenReturn(mockUsers);

        List<UserNameResponseDto> result = userController.test();

        // Assert
        assertEquals(2, result.size());
        assertEquals("User1", result.get(0).getUsername());
        assertEquals("User2", result.get(1).getUsername());
        verify(userService, times(1)).allUsers();
    }

    @Test
    void createUser_shouldCallServiceAndReturnOk() {
        UserNewRequestDto mockRequest = new UserNewRequestDto();
        mockRequest.setUsername("New User");

        ResponseEntity<String> response = userController.createUser(mockRequest);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        verify(userService, times(1)).newUser(mockRequest);
    }
}
