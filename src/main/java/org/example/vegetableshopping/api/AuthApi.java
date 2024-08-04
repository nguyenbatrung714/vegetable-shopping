package org.example.vegetableshopping.api;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.vegetableshopping.dto.request.ChangePassword;
import org.example.vegetableshopping.dto.request.LoginRequest;
import org.example.vegetableshopping.dto.request.UserRequest;
import org.example.vegetableshopping.dto.response.UserResponse;
import org.example.vegetableshopping.security.jwt.JwtTokenProvider;
import org.example.vegetableshopping.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import static java.lang.System.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthApi {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;
    private final HttpSession session;

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(authService.login(loginRequest));
    }

    @PostMapping
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(authService.register(userRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateAccount(@PathVariable("id") Integer id,
                                                      @RequestBody UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(authService.updateAccount(id, userRequest));
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<UserResponse> changePassword(@PathVariable("id") Integer id,
                                                       @Valid @RequestBody ChangePassword changePassword) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(authService.changePassword(id, changePassword));
    }

    @GetMapping()
    public ResponseEntity<UserResponse> getUser(@RequestParam String username) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(authService.findByUserName(username));
    }


    @GetMapping("/login-google-success")
    public ResponseEntity<String> user(@AuthenticationPrincipal OAuth2User principal) {
        String token = (String) session.getAttribute("token");
        return ResponseEntity.status(HttpStatus.OK)
                .body(token);
    }

}
