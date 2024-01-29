package com.example.validateLogin.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseRegister {
    private String firstName;
    private String lastName;
    private String email;
}
