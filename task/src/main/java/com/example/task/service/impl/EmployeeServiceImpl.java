package com.example.task.service.impl;

import com.example.task.entity.*;
import com.example.task.repository.EmployeeRepository;
import com.example.task.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository repository;

    @Autowired
    RestTemplate restTemplate;

    @Value("${spring.security.oauth2.client.registration.oauth2-client-credentials.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.oauth2-client-credentials.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.oauth2-client-credentials.authorization-grant-type}")
    private String grantType;

    @Override
    public List<Employee> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Employee> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("grant_type", grantType);
        map.add("username", loginRequest.getUsername());
        map.add("password", loginRequest.getPassword());

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, headers);

        ResponseEntity<LoginResponse> response = restTemplate.postForEntity("http://localhost:8180/auth/realms/task/protocol/openid-connect/token", httpEntity, LoginResponse.class);
        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<LogoutResponse> logout(TokenRequest tokenRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("refresh_token", tokenRequest.getToken());

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, headers);

        ResponseEntity<LogoutResponse> response = restTemplate.postForEntity("http://localhost:8180/auth/realms/task/protocol/openid-connect/logout", httpEntity, LogoutResponse.class);

        LogoutResponse res = new LogoutResponse();
        if (response.getStatusCode().is2xxSuccessful()) {
            res.setMessage("Logged out successfully");
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<IntrospectResponse> introspect(TokenRequest tokenRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("token", tokenRequest.getToken());

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, headers);

        ResponseEntity<IntrospectResponse> response = restTemplate.postForEntity("http://localhost:8180/auth/realms/task/protocol/openid-connect/token/introspect", httpEntity, IntrospectResponse.class);

        return new ResponseEntity<>(response.getBody() , HttpStatus.OK);
    }

}
