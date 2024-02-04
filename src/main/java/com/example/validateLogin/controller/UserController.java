package com.example.validateLogin.controller;

import com.example.validateLogin.dto.*;
import com.example.validateLogin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {
    final private UserService service;

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Response> getAll(@RequestHeader( value ="Authorization") String token) {

        return service.findAll(token);

    }
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    Response getById(@PathVariable(value = "id") Long id){

       return service.findById(id);

    }


}
