package com.david.spring_boot_ecommerce_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {
    @NotBlank(message = "Username é obrigatório!")
    private String username;

    @NotBlank(message = "Password é obrigatório!")
    @Size(min = 6, message = "Password deve ter pelo menos 6 caracteres!")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
