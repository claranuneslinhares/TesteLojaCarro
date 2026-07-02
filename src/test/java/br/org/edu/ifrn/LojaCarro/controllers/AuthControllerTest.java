package br.org.edu.ifrn.LojaCarro.controllers;

import br.org.edu.ifrn.LojaCarro.dto.LoginRequest;
import br.org.edu.ifrn.LojaCarro.model.Role;
import br.org.edu.ifrn.LojaCarro.model.Usuario;
import br.org.edu.ifrn.LojaCarro.repository.UsuarioRepository;
import br.org.edu.ifrn.LojaCarro.security.CustomUserDetailsService;
import br.org.edu.ifrn.LojaCarro.security.JwtFilter;
import br.org.edu.ifrn.LojaCarro.security.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private JwtFilter jwtFilter;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;


    @Test
    void deveRealizarLogin() throws Exception {

        LoginRequest request = new LoginRequest();

        request.setUsername("gerente");
        request.setPassword("123");

        Usuario usuario = new Usuario();

        usuario.setUsername("gerente");
        usuario.setPassword("123");
        usuario.setRole(Role.GERENTE);

        when(usuarioRepository.findByUsername("gerente"))
                .thenReturn(Optional.of(usuario));

        when(jwtService.generateToken(any()))
                .thenReturn("TOKEN_TESTE");

        mockMvc.perform(post("/auth/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token")
                        .value("TOKEN_TESTE"));
    }

    @Test
    void deveCadastrarUsuario() throws Exception {

        Usuario usuario = new Usuario();

        usuario.setUsername("vendedor");
        usuario.setPassword("123");
        usuario.setRole(Role.VENDEDOR);

        when(passwordEncoder.encode(anyString()))
                .thenReturn("senhaCriptografada");

        when(usuarioRepository.save(any()))
                .thenReturn(usuario);

        mockMvc.perform(post("/auth/usuarios")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username")
                        .value("vendedor"));
    }
}