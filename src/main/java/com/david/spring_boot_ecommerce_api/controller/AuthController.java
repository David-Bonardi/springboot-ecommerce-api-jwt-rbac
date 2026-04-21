package com.david.spring_boot_ecommerce_api.controller;

import com.david.spring_boot_ecommerce_api.dto.AuthResponse;
import com.david.spring_boot_ecommerce_api.dto.LoginRequest;
import com.david.spring_boot_ecommerce_api.dto.RegisterRequest;
import com.david.spring_boot_ecommerce_api.model.User;
import com.david.spring_boot_ecommerce_api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
// Recebe JSON, converte para DTO, chama o service e devolve resposta
public class AuthController {

    private UserService service;

    public AuthController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterRequest request) {
        // DTO -> Entity
        // O request representa o JSON de entrada
        // Aqui transformamos isso em um User da aplicação
        User user = new  User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());

        service.register(user);
        return "Usuário cadastrado com sucesso!";
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        // Chama a regra de login e recebe o JWT
        String token = service.login(request.getUsername(), request.getPassword());


        // Retorna o token organizado em um DTO de resposta
        return new AuthResponse(token);
    }
}
