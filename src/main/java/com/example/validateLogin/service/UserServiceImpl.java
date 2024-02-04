package com.example.validateLogin.service;

import com.example.validateLogin.config.Jwt;
import com.example.validateLogin.domain.User;
import com.example.validateLogin.dto.*;
import com.example.validateLogin.mapper.UserMapper;
import com.example.validateLogin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    final private UserRepository repository;
    final private PasswordEncoder passwordEncoder;
    final private UserMapper mapper;
    final private Jwt jwt;

    @Override
    public List<Response> findAll(String token) {

       if (jwt.validateToken(token) == true ){
           return mapper.entityToResponses(repository.findAll());
       }else return null;
    }

    @Override
    public Response findById(Long id) {

        return mapper.entityToResponse(repository.findById(id).get());
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public ResponseRegister register(Request request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        return mapper.entityToResponseR(repository.save(user));

    }

    @Override
    public Response findByEmail(String email) {
        User user = repository.findByEmail(email).get();
        return mapper.entityToResponse(user);
    }

    @Override
    public ResponseLogin validateLogin(LoginRequest request) {
        var user = findByEmail(request.getEmail());

        if (user != null && passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseLogin.builder()
                    .token(jwt.create(String.valueOf(user.getId()), user.getFirstName(), user.getEmail()))
                    .email(user.getEmail())
                    .lastName(user.getLastName())
                    .firstName(user.getFirstName())
                    .build();
        } else {
            throw new RuntimeException("User not valid");
        }

    }



}
