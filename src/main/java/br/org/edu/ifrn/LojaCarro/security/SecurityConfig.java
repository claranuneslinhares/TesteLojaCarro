package br.org.edu.ifrn.LojaCarro.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {

        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth

                        // libera login
                        .requestMatchers("/auth/**").permitAll()

                        // apenas gerente pode cadastrar usuários
                        .requestMatchers(HttpMethod.POST, "/usuarios")
                        .hasRole("GERENTE")

                        // gerente pode excluir carros
                        .requestMatchers(HttpMethod.DELETE, "/carro/**")
                        .hasRole("GERENTE")

                        // gerente e vendedor podem cadastrar
                        .requestMatchers(HttpMethod.POST, "/carro/**")
                        .hasAnyRole("GERENTE", "VENDEDOR")

                        // gerente e vendedor podem atualizar
                        .requestMatchers(HttpMethod.PUT, "/carro/**")
                        .hasAnyRole("GERENTE", "VENDEDOR")

                        // qualquer usuário autenticado pode consultar
                        .requestMatchers(HttpMethod.GET, "/carro/**")
                        .authenticated()

                        .anyRequest().authenticated()
                )

                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}