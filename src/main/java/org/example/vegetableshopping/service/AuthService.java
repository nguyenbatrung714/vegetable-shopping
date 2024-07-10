package org.example.vegetableshopping.service;

import org.example.vegetableshopping.dto.request.LoginRequest;

public interface AuthService {

    String login(LoginRequest loginRequest);

}
