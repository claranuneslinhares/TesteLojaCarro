package br.org.edu.ifrn.LojaCarro.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    void deveTestarGettersESetters() {

        Usuario usuario = new Usuario();

        usuario.setId(1L);
        usuario.setUsername("gerente");
        usuario.setPassword("123");
        usuario.setRole(Role.GERENTE);

        assertEquals(1L, usuario.getId());
        assertEquals("gerente", usuario.getUsername());
        assertEquals("123", usuario.getPassword());
        assertEquals(Role.GERENTE, usuario.getRole());
    }
}
