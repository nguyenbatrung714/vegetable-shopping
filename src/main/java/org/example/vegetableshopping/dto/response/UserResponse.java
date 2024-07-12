package org.example.vegetableshopping.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.vegetableshopping.entity.Role;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Builder
public class UserResponse {
    private Integer userId;
    private String username;
    private String fullname;
    private String email;
    private String phoneNumber;
    private Date dayOfBirth;
    private String address;
    private String gender;
    private Boolean active;
    private Set<String> roles;
}
