package com.example.validateLogin.service;

import com.example.validateLogin.config.Jwt;
import com.example.validateLogin.domain.User;
import com.example.validateLogin.dto.LoginRequest;
import com.example.validateLogin.dto.Request;
import com.example.validateLogin.dto.Response;
import com.example.validateLogin.dto.ResponseLogin;
import com.example.validateLogin.mapper.UserMapper;
import com.example.validateLogin.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    final private UserRepository repository;
    final private PasswordEncoder passwordEncoder;
   final private UserMapper mapper;
   final private Jwt jwt;

    @Override
    public List<Response> findAll() {

        return mapper.entityToResponses(repository.findAll());
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
    public Response register(Request request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        return mapper.entityToResponse(repository.save(user));

      /*  if (Objects.nonNull(request)) {
            User user = mapper.requestToEntity(request);

            return mapper.entityToResponse(repository.save(user));
        }
        throw new RuntimeException("no request");*/
    }

    @Override
    public Response findByEmail(String email) {
        User user = repository.findByEmail(email).get();
        return mapper.entityToResponse(user);
    }

    @Override
    public Response validateLogin(LoginRequest request) {
        Response response = null;
        User user = findByUsername(request.getEmail());
            if (Objects.nonNull(user)){
                response = Response.builder()
                        .token(jwt.create(String.valueOf(user.getId()),user.getFirstName(), user.getEmail()))
                        .email(user.getEmail())
                        .lastName(user.getLastName())
                        .firstName(user.getFirstName())
                        .build();
            }else {
                throw new RuntimeException("User not valid");
            }
            return response;
/*
        log.info("Validate User Mobil App ( " + user.getEmail() + " )");
        final String resultPassword = user.getPassword();
        final String resulEmail = user.getEmail();
        return mapper.entityToResponse(verifyPassword(resulEmail, resultPassword));*/
    }

    private User verifyPassword(String resulEmail, String resultPassword) {
        ResponseLogin responseLogin = null;
        User user = findByUsername(resulEmail);
        if (Objects.nonNull(user)) {
            if (user.getPassword().equals(resultPassword)) {
                responseLogin = buildUser(resulEmail, user);

            }
        }
        if (Objects.isNull(user)) {
            log.error("Password not valid : User not found, verify your email :  " + resulEmail + " or password: " + resultPassword);
            throw new RuntimeException("User not found, verify your email.");
        }
        return user;
    }

    private ResponseLogin buildUser(String resulEmail, User user) {
        return ResponseLogin.builder()
                .lastName(user.getLastName())
                .email(resulEmail)
                .firstName(user.getFirstName())
                .token(jwt.create(String.valueOf(user.getId()), user.getFirstName(), resulEmail))
                .build();
    }


    public boolean validateToken(String token) {
        String userID = null;
        try {
            ResponseEntity<String> tokenExtra = jwt.getKey(token);
            userID = tokenExtra.getBody();
        } catch (Exception e) {
            new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        return userID != null ? true : false;
    }

    public void existById(final Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("User no found");
        }
    }

    public static String generateSHA256Hash(String password) {
        try {
            // Obtén una instancia de MessageDigest con el algoritmo SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Convierte la contraseña en bytes
            byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);

            // Calcula el hash SHA-256 de los bytes de la contraseña
            byte[] hashBytes = digest.digest(passwordBytes);

            // Convierte el hash en una representación hexadecimal
            StringBuilder hexHash = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    hexHash.append('0');
                }
                hexHash.append(hex);
            }

            return hexHash.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }


    public User findByUsername(final String email) {
        log.info("findByUsernameMobile in email : " + email);
        User user = repository.findByEmail(email).get();
        return user;
    }
    public Response findCurrentUser(String token) {
        try {
            log.info("Inf Curren User ");
            return jwt.findCurrentUser(token);
        }catch (ExpiredJwtException e){
            throw new ExpiredJwtException(null, null, "Token expirado");
        }
    }
}
