package org.example.vegetableshopping.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.vegetableshopping.converter.UserConverter;
import org.example.vegetableshopping.dto.response.UserResponse;
import org.example.vegetableshopping.entity.User;
import org.example.vegetableshopping.repository.UserRepository;
import org.example.vegetableshopping.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Page<UserResponse> getUsers(int page, int size, String sort) {
        Sort sorting = Sort.unsorted();
        if (sort != null && !sort.isEmpty()) {
            sorting = sort.equalsIgnoreCase("asc")
                    ? Sort.by(Sort.Direction.ASC, "username")
                    : Sort.by(Sort.Direction.DESC, "username");
        }

        Pageable pageable = PageRequest.of(page, size, sorting);
        Page<User> users = userRepository.findAll(pageable);

        return users.map(UserConverter::toUserResponse);
    }
}
