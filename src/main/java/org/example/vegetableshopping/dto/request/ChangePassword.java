package org.example.vegetableshopping.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePassword {
        @NotBlank(message = "Ole password is required")
        private String oldPassword;

        @NotBlank(message = "New password is required")
        private String newPassword;
}
