package org.example.vegetableshopping.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.vegetableshopping.converter.UserConverter;
import org.example.vegetableshopping.dto.request.ChangePassword;
import org.example.vegetableshopping.dto.request.LoginRequest;
import org.example.vegetableshopping.dto.request.UserRequest;
import org.example.vegetableshopping.dto.response.UserResponse;
import org.example.vegetableshopping.entity.Role;
import org.example.vegetableshopping.entity.User;
import org.example.vegetableshopping.enums.RoleType;
import org.example.vegetableshopping.exception.AuthenticationException;
import org.example.vegetableshopping.exception.ResourceExitsException;
import org.example.vegetableshopping.exception.ResourceNotFoundException;
import org.example.vegetableshopping.repository.RoleRepository;
import org.example.vegetableshopping.repository.UserRepository;
import org.example.vegetableshopping.security.config.SecurityConfig;
import org.example.vegetableshopping.security.jwt.JwtTokenProvider;
import org.example.vegetableshopping.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public String login(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenProvider.generateToken(loginRequest.getUsername());
    }

    @Override
    public UserResponse register(UserRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new ResourceExitsException("User", "username", userRequest.getUsername());
        }

        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new ResourceExitsException("User", "email", userRequest.getEmail());
        }

        userRequest.setPassword(encoder.encode(userRequest.getPassword()));

        User user = new User();

        Set<Role> roles = new HashSet<>();

        if (userRequest.getRoles() == null || userRequest.getRoles().isEmpty()) {
            Role userRole = roleRepository.findByName(RoleType.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Role is not found."));
            roles.add(userRole);
        } else {
            for (String roleName : userRequest.getRoles()) {
                try {
                    RoleType eRole = RoleType.valueOf(roleName.toUpperCase());
                    Role role = roleRepository.findByName(eRole)
                            .orElseThrow(() -> new RuntimeException("Role " + roleName + " is not found."));
                    roles.add(role);
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException("Invalid role name " + roleName);
                }
            }
        }

        user.setRoles(roles);
        UserConverter.toUser(user, userRequest);
        userRepository.save(user);

        return UserConverter.toUserResponse(user);
    }

    @Override
    public UserResponse updateAccount(Integer id, UserRequest userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", "" + id));

        if (userRequest.getRoles() != null && !userRequest.getRoles().isEmpty()) {
            Set<Role> roles = new HashSet<>();
            for (String roleName : userRequest.getRoles()) {
                try {
                    RoleType eRole = RoleType.valueOf(roleName.toUpperCase());
                    Role role = roleRepository.findByName(eRole)
                            .orElseThrow(() -> new RuntimeException("Role " + roleName + " is not found."));
                    roles.add(role);
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException("Invalid role name " + roleName);
                }
            }
            user.setRoles(roles);
        }

        UserConverter.toUser(user, userRequest);
        userRepository.save(user);

        return UserConverter.toUserResponse(user);
    }

    @Override
    public UserResponse changePassword(Integer id, ChangePassword changePassword) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", "" + id));

        if (!encoder.matches(changePassword.getOldPassword(), user.getPassword())) {
            throw new AuthenticationException("Old password is incorrect");
        }

        user.setPassword(encoder.encode(changePassword.getNewPassword()));
        userRepository.save(user);

        return UserConverter.toUserResponse(user);
    }

    @Override
    public UserResponse findByUserName(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new ResourceExitsException("User", "username", username);
        }

        return UserConverter.toUserResponse(user);
    }

}
