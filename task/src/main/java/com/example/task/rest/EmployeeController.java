package com.example.task.rest;

import com.example.task.entity.*;
import com.example.task.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@Validated
@RequestMapping("/api/v1/task/employees")
public class EmployeeController {

    @Autowired
    EmployeeService service;

    @GetMapping()
    @RolesAllowed("admin")
    public List<Employee> getAll() {
        return service.getAll();
    }

    @GetMapping("/{employee-id}")
    @RolesAllowed("user")
    public Optional<Employee> getById(@PathVariable("employee-id") Long emloyeeId) {
        return service.getById(emloyeeId);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return service.login(loginRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logout(@RequestBody TokenRequest tokenRequest) {
        return service.logout(tokenRequest);
    }

    @PostMapping("/introspect")
    public ResponseEntity<IntrospectResponse> introspect(@RequestBody TokenRequest tokenRequest) {
        return service.introspect(tokenRequest);
    }



}
