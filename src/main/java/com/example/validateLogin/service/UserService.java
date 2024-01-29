package com.example.validateLogin.service;

import com.example.validateLogin.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<Response> findAll();
    Response findById(Long id);
    void delete(Long id);
    ResponseRegister register(Request request);
    Response findByEmail(String email);
    ResponseLogin validateLogin(LoginRequest request);


}
