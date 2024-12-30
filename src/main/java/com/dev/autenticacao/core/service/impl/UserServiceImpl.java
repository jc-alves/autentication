package com.dev.autenticacao.core.service.impl;

import com.dev.autenticacao.application.api.v1.dto.mapper.UserMapper;
import com.dev.autenticacao.application.api.v1.dto.request.UserNewRequestDto;
import com.dev.autenticacao.application.api.v1.dto.response.UserNameResponseDto;
import com.dev.autenticacao.core.domain.Users;
import com.dev.autenticacao.core.exception.EventFullException;
import com.dev.autenticacao.core.infra.RestExceptionHandler;
import com.dev.autenticacao.core.repository.UserRepository;
import com.dev.autenticacao.core.service.UserService;
import com.dev.autenticacao.core.util.PasswordValidator;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private Logger logger;

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

    /**
     *
     * @return
     */
    @Override
    public List<UserNameResponseDto> allUsers() {
        List<Users> users = userRepository.findAll();

        return users.stream()
                .map(userMapper::usersToNameResponseDto)
                .collect(Collectors.toList());
    }

    /**
     *
     * @param userNewRequestDto
     */
    @Override
    public void newUser(UserNewRequestDto userNewRequestDto) {

        try {

            if(isExistingUser(userNewRequestDto.getUsername())) {
                throw new EventFullException("Usuario já cadastrado!");
            }

            if(isExistingEmail(userNewRequestDto.getEmail())) {
                throw new EventFullException("Email já cadastrado!");
            }

            if (!passwordValidator.validate(userNewRequestDto.getPassword())) {
                throw new EventFullException("Senha inválida. Deve ser forte.");
            }

            Users user = userMapper.userNemRequestToUsers(userNewRequestDto);
            user.setPassword(bCryptPasswordEncoder.encode(userNewRequestDto.getPassword()));
            userRepository.save(user);

        } catch (CannotGetJdbcConnectionException e) {
            logger.error("Banco de dados inacessível: {}", e.getMessage(), e);
            throw new EventFullException("O sistema está temporariamente indisponível. Tente novamente mais tarde.");
        } catch (DataAccessException e) {
            logger.error("Erro de banco de dados: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao processar os dados. Tente novamente mais tarde.", e);
        }

    }

    private boolean isExistingUser(String nameUser) {
        return userRepository.findUserByName(nameUser).isPresent();
    }

    private boolean isExistingEmail(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }
}
