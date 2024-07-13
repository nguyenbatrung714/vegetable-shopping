package org.example.vegetableshopping.service;

import org.example.vegetableshopping.dto.response.UserResponse;
import org.springframework.data.domain.Page;

public interface UserService {

    Page<UserResponse> getUsers(int page, int size, String sort);

    UserResponse getUser(Integer id);

    Page<UserResponse> searchByUsername(String name, int page, int size);

}
