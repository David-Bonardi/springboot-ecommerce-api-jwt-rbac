package com.david.spring_boot_ecommerce_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "users")// Define explicitamente o nome da tabela no banco
public class User {
    // ID gerado automaticamente pelo banco
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // username não pode ser nulo e não pode se repetir
    @Column(nullable = false, unique = true)
    private String username;

    // A senha existe no banco, mas não deve aparecer nas respostas JSON da API
    @JsonIgnore
    @Column(nullable = false)
    private String password;

    // Diz ao JPA para salvar o enum como texto no banco ("USER", "ADMIN")
    // e não como número (0, 1)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public User() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
