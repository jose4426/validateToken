package com.example.validateLogin.controller;

import com.example.validateLogin.dto.LoginRequest;
import com.example.validateLogin.dto.Request;
import com.example.validateLogin.dto.Response;
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
   public Response saveUser(@RequestBody Request request){

         Response response= service.register(request);
        return response;

    }
    //@Operation(summary = "User mobil login")
    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    Response UserLogin(@RequestBody LoginRequest request){


        return  service.validateLogin(request);

    }

}
