package com.david.spring_boot_ecommerce_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

// Classe de configuração do Spring Security
@Configuration
// Habilita anotações como @PreAuthorize
@EnableMethodSecurity
public class SecurityConfig {


    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        // Registra o encoder como bean para poder injetar no service
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        http    // Como a autenticação será por JWT (stateless),
                // não precisamos do CSRF padrão baseado em sessão/cookie.
                .csrf(csrf -> csrf.disable())
                // Stateless = o servidor não guarda sessão.
                // Cada requisição precisa levar sua autenticação (o token).
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Tudo dentro de /auth é público:
                // registro e login
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/swagger-ui.html").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/v3/api-docs").permitAll()
                        // Qualquer outra rota exige autenticação
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
