package org.example.vegetableshopping.security.service;

import lombok.RequiredArgsConstructor;
import org.example.vegetableshopping.entity.Role;
import org.example.vegetableshopping.entity.User;
import org.example.vegetableshopping.enums.RoleType;
import org.example.vegetableshopping.repository.RoleRepository;
import org.example.vegetableshopping.repository.UserRepository;
import org.example.vegetableshopping.security.jwt.JwtTokenProvider;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private UserRepository userRepository;
    private JwtTokenProvider jwtTokenProvider;
    private RoleRepository roleRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        DefaultOAuth2UserService oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");

        User user = userRepository.findByEmail(email);
        if (user == null) {
            user = new User();
            user.setEmail(email);
            user.setUsername(email);

            Set<Role> roles = new HashSet<>();

            Role defaultRole = roleRepository.findByName(RoleType.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Role is not found."));
            roles.add(defaultRole);
            
            user.setRoles(roles);

            userRepository.save(user);
        }

        String token = jwtTokenProvider.generateToken(email);

        Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());
        attributes.put("token", token);

        return new DefaultOAuth2User(oAuth2User.getAuthorities(), attributes, "email");
    }

}

