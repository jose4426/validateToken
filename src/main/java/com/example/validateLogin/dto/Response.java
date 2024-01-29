package com.example.validateLogin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String token;


}
