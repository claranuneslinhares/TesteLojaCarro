package br.org.edu.ifrn.lojacarro.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarroTest {

    @Test
    void deveTestarGettersESetters() {

        Carro carro = new Carro();

        carro.setId(1L);
        carro.setModelo("Corolla");
        carro.setAno(2024);

        assertEquals(1L, carro.getId());
        assertEquals("Corolla", carro.getModelo());
        assertEquals(2024, carro.getAno());
    }
}