package com.example.validateLogin.mapper;

import com.example.validateLogin.domain.User;
import com.example.validateLogin.dto.Request;
import com.example.validateLogin.dto.Response;
import com.example.validateLogin.dto.ResponseRegister;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    Response entityToResponse(User user);
    User requestToEntity(Request request);
    List<Response> entityToResponses(List<User> user);
    ResponseRegister entityToResponseR(User user);

}
