package com.david.spring_boot_ecommerce_api.service;

import com.david.spring_boot_ecommerce_api.model.Role;
import com.david.spring_boot_ecommerce_api.model.User;
import com.david.spring_boot_ecommerce_api.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

// Service = regra de negócio.
// Controller não deve conter lógica; ele só recebe a requisição e delega.
@Service
public class UserService {

    private final UserRepository repo;
    private final BCryptPasswordEncoder encoder;
    private final JwtService jwtService;

    public UserService(UserRepository repo, BCryptPasswordEncoder encoder, JwtService jwtService) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }

    // Antes de salvar, verifica se o username já existe no banco
    public User register(User user) {
        if (repo.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Usuário já existente!");
        }

        // Aqui transforma a senha em hash BCrypt
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(Role.USER);

        // Persiste o usuário no banco
        return repo.save(user);
    }

    public String login(String username, String password) {

        // Procura usuário pelo username
        User user = repo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Credenciais Inválidas!"));

        // Compara a senha enviada com o hash salvo no banco
        if (!encoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Credenciais Inválidas!");
        }

        // Se a senha estiver correta, gera um token JWT
        return jwtService.generateToken(user.getUsername());
    }
}
