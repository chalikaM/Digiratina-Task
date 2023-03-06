package com.example.task.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private String access_token;

    private String refresh_token;

    private String expiry_in;

    private String refresh_expiry_in;

    private String token_type;
}
