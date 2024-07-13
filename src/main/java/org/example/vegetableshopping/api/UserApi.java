package org.example.vegetableshopping.api;

import lombok.RequiredArgsConstructor;
import org.example.vegetableshopping.dto.response.UserResponse;
import org.example.vegetableshopping.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserApi {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserResponse>> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                                       @RequestParam(value = "size", defaultValue = "15") int size,
                                                       @RequestParam(value = "sort", required = false) String sort) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getUsers(page, size, sort));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getUser(id));
    }

    @GetMapping("/search/by-username")
    public ResponseEntity<Page<UserResponse>> searchByUsername(@RequestParam String username,
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "15") int size) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.searchByUsername(username, page, size));
    }

}
