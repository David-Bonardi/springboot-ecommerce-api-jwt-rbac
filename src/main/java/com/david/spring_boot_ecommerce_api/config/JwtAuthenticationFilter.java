package com.david.spring_boot_ecommerce_api.config;

import com.david.spring_boot_ecommerce_api.model.User;
import com.david.spring_boot_ecommerce_api.repository.UserRepository;
import com.david.spring_boot_ecommerce_api.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {


        //Pega o Header Authorization
        String authHeader = request.getHeader("Authorization");

        //Se não tiver o token, continua
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        //remove "Bearer " e pega só o token
        String token = authHeader.substring(7);

        //Extrai o username do token
        String username = jwtService.extractUsername(token);

        //Se tem username e ainda não está autenticado
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){

            //Verifica se o usuário existe no banco
            var userOpt = userRepository.findByUsername(username);

            if (userOpt.isPresent() && jwtService.isTokenValid(token, username)) {
                User user = userOpt.get();

                // Constrói a role no formato que o Spring espera: ROLE_USER / ROLE_ADMIN
                SimpleGrantedAuthority authority =
                        new SimpleGrantedAuthority("ROLE_" + user.getRole().name());
                //cria o objeto de autenticação
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                List.of(authority)
                        );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                //fala pro spring que esse user está autenticado
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
