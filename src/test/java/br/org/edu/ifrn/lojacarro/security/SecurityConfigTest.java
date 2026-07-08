package br.org.edu.ifrn.lojacarro.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class SecurityConfigTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SecurityFilterChain securityFilterChain;

    @Test
    void deveCarregarBeansDeSeguranca() {
        assertNotNull(passwordEncoder);
        assertNotNull(authenticationManager);
        assertNotNull(securityFilterChain);
    }

    @Test
    void deveCriptografarSenha() {
        String senha = passwordEncoder.encode("123456");

        assertNotNull(senha);
    }
}