package org.example.vegetableshopping.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.example.vegetableshopping.entity.Role;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class UserRequest {

    @NotBlank(message = "Username is required")
    private String username;

    private String fullname;

    @NotBlank(message = "Password is required")
    private String password;

    @NotNull(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    private String phoneNumber;
    private Date dayOfBirth;
    private String address;
    private String gender;
    private Boolean active = true;
    private Set<String> roles;
}