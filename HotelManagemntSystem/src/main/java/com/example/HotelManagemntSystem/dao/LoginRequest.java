package com.example.HotelManagemntSystem.dao;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Email is required")
    private String email;

    public @NotBlank(message = "Password is required") String getPassword() {
        return password;
    }

    public @NotBlank(message = "Email is required") String getEmail() {
        return email;
    }

    @NotBlank(message = "Password is required")
    private String password;
}
