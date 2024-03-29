package com.example.validateLogin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class ResponseLogin {
    private String firstName;
    private String lastName;
    private String email;
    private String token;
}
