package com.example.validateLogin.service;

import com.example.validateLogin.dto.LoginRequest;
import com.example.validateLogin.dto.Request;
import com.example.validateLogin.dto.Response;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<Response> findAll();
    Response findById(Long id);
    void delete(Long id);
    Response register(Request request);
    Response findByEmail(String email);
    Response validateLogin(LoginRequest request);


}
