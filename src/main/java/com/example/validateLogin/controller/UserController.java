package com.example.validateLogin.controller;

import com.example.validateLogin.dto.*;
import com.example.validateLogin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class UserController {
    final private UserService service;

    //@Operation(summary = "Save User mobil")
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseRegister saveUser(@RequestBody Request request) {

        ResponseRegister response = service.register(request);
        return response;

    }

    //@Operation(summary = "User mobil login")
    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    ResponseLogin UserLogin(@RequestBody LoginRequest request) {


        return service.validateLogin(request);

    }

}
