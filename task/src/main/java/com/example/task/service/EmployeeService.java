package com.example.task.service;

import com.example.task.entity.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    List<Employee> getAll();

    Optional<Employee> getById(Long id);

    ResponseEntity<LoginResponse> login(LoginRequest loginRequest);

    ResponseEntity<LogoutResponse> logout(TokenRequest tokenRequest);

    ResponseEntity<IntrospectResponse> introspect(TokenRequest tokenRequest);
}
