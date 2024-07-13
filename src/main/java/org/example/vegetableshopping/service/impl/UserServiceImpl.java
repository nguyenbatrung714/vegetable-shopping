package org.example.vegetableshopping.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.vegetableshopping.converter.UserConverter;
import org.example.vegetableshopping.dto.response.UserResponse;
import org.example.vegetableshopping.entity.User;
import org.example.vegetableshopping.exception.ResourceNotFoundException;
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

    @Override
    public UserResponse getUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", "" + id));

        return UserConverter.toUserResponse(user);
    }

    @Override
    public Page<UserResponse> searchByUsername(String username, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = userRepository.findAllByUsernameContaining(username, pageable);

        return users.map(UserConverter::toUserResponse);
    }
}
