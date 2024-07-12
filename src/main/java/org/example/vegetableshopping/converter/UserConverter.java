package org.example.vegetableshopping.converter;

import org.example.vegetableshopping.dto.request.UserRequest;
import org.example.vegetableshopping.dto.response.UserResponse;
import org.example.vegetableshopping.entity.Role;
import org.example.vegetableshopping.entity.User;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserConverter {

    public static UserResponse toUserResponse(User user) {
        Set<String> roles = user.getRoles().stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toSet());
        return UserResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .fullname(user.getFullname())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .dayOfBirth(user.getDayOfBirth())
                .address(user.getAddress())
                .gender(user.getGender())
                .active(user.getActive())
                .roles(roles)
                .build();
    }

    public static void toUser(User user, UserRequest userRequest) {
        if (userRequest.getUsername() != null) {
            user.setUsername(userRequest.getUsername());
        }
        if (userRequest.getFullname() != null) {
            user.setFullname(userRequest.getFullname());
        }
        if (userRequest.getPassword() != null) {
            user.setPassword(userRequest.getPassword());
        }
        if (userRequest.getEmail() != null) {
            user.setEmail(userRequest.getEmail());
        }
        if (userRequest.getPhoneNumber() != null) {
            user.setPhoneNumber(userRequest.getPhoneNumber());
        }
        if (userRequest.getDayOfBirth() != null) {
            user.setDayOfBirth(userRequest.getDayOfBirth());
        }
        if (userRequest.getAddress() != null) {
            user.setAddress(userRequest.getAddress());
        }
        if (userRequest.getGender() != null) {
            user.setGender(userRequest.getGender());
        }
        if (userRequest.getActive() != null) {
            user.setActive(userRequest.getActive());
        }
    }

}
