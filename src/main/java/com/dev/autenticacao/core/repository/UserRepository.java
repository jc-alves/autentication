package com.dev.autenticacao.core.repository;


import com.dev.autenticacao.application.api.v1.dto.response.LoginResponse;
import com.dev.autenticacao.core.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM users u WHERE u.username = :username")
    Optional<Users> findUserByName(@Param("username") String username);

    @Query(nativeQuery = true, value = "SELECT * FROM users u WHERE u.email = :email")
    Optional<Users> findUserByEmail(@Param("email") String email);
}
