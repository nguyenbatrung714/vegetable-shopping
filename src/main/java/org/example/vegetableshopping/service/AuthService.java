package org.example.vegetableshopping.service;

import org.example.vegetableshopping.dto.request.LoginRequest;
import org.example.vegetableshopping.dto.request.UserRequest;
import org.example.vegetableshopping.dto.response.UserResponse;

public interface AuthService {

    String login(LoginRequest loginRequest);

    UserResponse register(UserRequest userRequest);

    UserResponse updateAccount(Integer id, UserRequest userRequest);

}
