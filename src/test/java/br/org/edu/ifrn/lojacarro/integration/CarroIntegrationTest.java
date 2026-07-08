package br.org.edu.ifrn.lojacarro.integration;

import br.org.edu.ifrn.lojacarro.model.Carro;
import br.org.edu.ifrn.lojacarro.repository.CarroRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CarroIntegrationTest {

    @Autowired
    private CarroRepository carroRepository;

    @Test
    void deveSalvarEBuscarCarro() {

        Carro carro = new Carro();
        carro.setModelo("Corolla");
        carro.setAno(2024);

        Carro salvo = carroRepository.save(carro);

        assertNotNull(salvo.getId());

        Carro encontrado = carroRepository
                .findById(salvo.getId())
                .orElse(null);

        assertNotNull(encontrado);
        assertEquals("Corolla", encontrado.getModelo());
        assertEquals(2024, encontrado.getAno());
    }
}